package by.training.finalp.service;

import by.training.finalp.exception.ServiceException;

public interface ServiceFactory {
    <Type extends Service> Type getService(Class<Type> key) throws ServiceException;

    void close();
}
