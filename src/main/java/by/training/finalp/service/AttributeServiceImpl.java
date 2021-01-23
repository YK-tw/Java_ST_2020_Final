package by.training.finalp.service;

import by.training.finalp.dao.mysql.AttributeDAO;
import by.training.finalp.entity.Attribute;
import by.training.finalp.exception.DAOException;
import by.training.finalp.exception.ServiceException;

import java.util.List;

public class AttributeServiceImpl extends ServiceImpl implements AttributeService {
    @Override
    public Attribute findById(final Integer id) throws ServiceException {
        try {
            AttributeDAO dao = transaction.createDao(AttributeDAO.class);
            return dao.read(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void save(final Attribute attribute) throws ServiceException {
        try {
            AttributeDAO dao = transaction.createDao(AttributeDAO.class);
            if (attribute.getId() != null) {
                dao.update(attribute);
            } else {
                dao.create(attribute);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(final Integer id) throws ServiceException {
        try {
            AttributeDAO dao = transaction.createDao(AttributeDAO.class);
            dao.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Attribute> getAll() throws ServiceException {
        try {
            AttributeDAO dao = transaction.createDao(AttributeDAO.class);
            return dao.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
