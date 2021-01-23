package by.training.finalp.dao;

import by.training.finalp.dao.connection.ConnectionPool;
import by.training.finalp.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionFactoryImpl implements TransactionFactory {
    private static Logger logger = LogManager.getLogger(TransactionFactoryImpl.class);
    private Connection connection;

    public TransactionFactoryImpl() throws DAOException {
        connection = ConnectionPool.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("It is impossible to turn off autocommiting for database connection", e);
            throw new DAOException(e);
        }
    }

    @Override
    public Transaction createTransaction() {
        return new TransactionImpl(connection);
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
        }
    }
}
