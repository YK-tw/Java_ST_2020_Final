package by.training.finalp.dao.connection;

import by.training.finalp.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static Logger logger = LogManager.getLogger(ConnectionPool.class);

    private String url;
    private String user;
    private String password;
    private int maxSize;
    private int checkConnectionTimeout;

    private final BlockingQueue<PooledConnection> freeConnections = new LinkedBlockingQueue<>();
    private final  Set<PooledConnection> usedConnections = new ConcurrentSkipListSet<>();

    private final ReentrantLock lock = new ReentrantLock();

    private ConnectionPool() {
    }

    public Connection getConnection() throws DAOException {
        PooledConnection connection = null;
        while (connection == null) {
            try {
                if (!freeConnections.isEmpty()) {
                    connection = freeConnections.take();
                    lock.lock();
                    if (!connection.isValid(checkConnectionTimeout)) {
                        try {
                            connection.getConnection().close();
                            lock.unlock();
                        } catch (SQLException e) {
                        }
                        connection = null;
                        lock.unlock();
                    }
                } else if (usedConnections.size() < maxSize) {
                    connection = createConnection();
                } else {
                    logger.error("The limit of number of database connections is exceeded");
                    throw new DAOException();
                }
            } catch (InterruptedException | SQLException e) {
                logger.error("It is impossible to connect to a database", e);
                throw new DAOException(e);
            }
        }
        usedConnections.add(connection);
        lock.unlock();
        logger.debug(String.format("Connection was received from pool. Current pool size: %d used connections; %d free connection", usedConnections.size(), freeConnections.size()));
        return connection;
    }

    void freeConnection(PooledConnection connection) {
        try {
            lock.lock();
            if (connection.isValid(checkConnectionTimeout)) {
                connection.clearWarnings();
                connection.setAutoCommit(true);
                usedConnections.remove(connection);
                freeConnections.put(connection);
                logger.debug(String.format("Connection was returned into pool. Current pool size: %d used connections; %d free connection", usedConnections.size(), freeConnections.size()));
            }
            lock.unlock();
        } catch (SQLException | InterruptedException e1) {
            logger.warn("It is impossible to return database connection into pool", e1);
            try {
                connection.getConnection().close();
            } catch (SQLException e2) {
            }
        }
    }

    public void init(String url, String user, String password, int startSize, int maxSize, int checkConnectionTimeout) throws DAOException {
        try {
            destroy();
            this.url = url;
            this.user = user;
            this.password = password;
            this.maxSize = maxSize;
            this.checkConnectionTimeout = checkConnectionTimeout;
            for (int counter = 0; counter < startSize; counter++) {
                freeConnections.put(createConnection());
            }
        } catch (SQLException | InterruptedException e) {
            logger.fatal("It is impossible to initialize connection pool", e);
            throw new DAOException(e);
        }
    }

    private static ConnectionPool instance = new ConnectionPool();

    public static ConnectionPool getInstance() {
        return instance;
    }

    private PooledConnection createConnection() throws SQLException, DAOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return new PooledConnection(DriverManager.getConnection(url, user, password));
        } catch (ClassNotFoundException e) {
            throw new DAOException(e);
        }
    }

    public void destroy() {
        lock.lock();
        usedConnections.addAll(freeConnections);
        freeConnections.clear();
        for (PooledConnection connection : usedConnections) {
            try {
                connection.getConnection().close();
            } catch (SQLException e) {
            }
        }
        usedConnections.clear();
        lock.unlock();
    }

    @Override
    protected void finalize() throws Throwable {
        destroy();
    }
}
