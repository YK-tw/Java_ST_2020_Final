package by.training.finalp.service;

import by.training.finalp.entity.Attribute;
import by.training.finalp.exception.ServiceException;

import java.util.List;

public interface AttributeService extends Service {

    Attribute findById(final Integer id) throws ServiceException;

    void save(final Attribute attribute) throws ServiceException;

    void delete(final Integer id) throws ServiceException;

    List<Attribute> getAll() throws ServiceException;
}
