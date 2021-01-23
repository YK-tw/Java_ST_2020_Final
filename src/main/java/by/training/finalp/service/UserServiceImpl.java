package by.training.finalp.service;

import by.training.finalp.dao.mysql.UserDAO;
import by.training.finalp.entity.User;
import by.training.finalp.exception.DAOException;
import by.training.finalp.exception.ServiceException;

import java.util.List;

public class UserServiceImpl extends ServiceImpl implements UserService {
    @Override
    public User findById(final Integer id) throws ServiceException {
        try {
            UserDAO dao = transaction.createDao(UserDAO.class);
            return dao.read(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void save(final User user) throws ServiceException {
        try {
            UserDAO dao = transaction.createDao(UserDAO.class);
            if (user.getId() != null) {
                dao.update(user);
            } else {
                user.setId(dao.create(user));
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(final Integer id) throws ServiceException {
        try {
            UserDAO dao = transaction.createDao(UserDAO.class);
            dao.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User findByLoginAndPassword(String login, String password) throws ServiceException {
        try {
            UserDAO dao = transaction.createDao(UserDAO.class);
            return dao.read(login, password);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> getAll() throws ServiceException {
        try {
            UserDAO dao = transaction.createDao(UserDAO.class);
            return dao.readAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
