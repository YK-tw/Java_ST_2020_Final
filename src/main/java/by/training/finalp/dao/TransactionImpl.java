package by.training.finalp.dao;

import by.training.finalp.dao.mysql.*;
import by.training.finalp.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionImpl implements Transaction {
    private static Logger logger = LogManager.getLogger(TransactionImpl.class);

    private static Map<Class<? extends DAO<?>>, Class<? extends BaseDAO>> classes = new ConcurrentHashMap<>();

    static {
        classes.put(AttributeDAO.class, AttributeDAOImpl.class);
        classes.put(OrderDAO.class, OrderDAOImpl.class);
        classes.put(ProductDAO.class, ProductDAOImpl.class);
        classes.put(UserDAO.class, UserDAOImpl.class);
    }

    private Connection connection;

    public TransactionImpl(Connection connection) {
        this.connection = connection;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <Type extends DAO<?>> Type createDao(Class<Type> key) throws DAOException {
        Class<? extends BaseDAO> value = classes.get(key);
        if (value != null) {
            try {
                BaseDAO dao = value.newInstance();
                dao.setConnection(connection);
                return (Type) dao;
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error("It is impossible to create data access object", e);
                throw new DAOException(e);
            }
        }
        return null;
    }

    @Override
    public void commit() throws DAOException {
        try {
            connection.commit();
        } catch (SQLException e) {
            logger.error("It is impossible to commit transaction", e);
            throw new DAOException(e);
        }
    }

    @Override
    public void rollback() throws DAOException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.error("It is impossible to rollback transaction", e);
            throw new DAOException(e);
        }
    }
}
