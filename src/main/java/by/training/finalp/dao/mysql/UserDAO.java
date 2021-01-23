package by.training.finalp.dao.mysql;

import by.training.finalp.entity.User;
import by.training.finalp.exception.DAOException;

import java.util.List;

public interface UserDAO extends DAO<User> {
    User read(final String login, final String password) throws DAOException;

    List<User> readAll() throws DAOException;
}
