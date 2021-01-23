package by.training.finalp.service;

import by.training.finalp.entity.User;
import by.training.finalp.exception.ServiceException;

import java.util.List;

public interface UserService extends Service {

    User findById(final Integer id) throws ServiceException;

    void save(final User user) throws ServiceException;

    void delete(final Integer id) throws ServiceException;

    User findByLoginAndPassword(final String login, final String password) throws ServiceException;

    List<User> getAll() throws ServiceException;
}
