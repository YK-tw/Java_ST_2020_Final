package by.training.finalp.dao;

import by.training.finalp.dao.mysql.DAO;
import by.training.finalp.exception.DAOException;

public interface Transaction {
    <Type extends DAO<?>> Type createDao(Class<Type> key) throws DAOException;

    void commit() throws DAOException;

    void rollback() throws DAOException;
}
