package by.training.finalp.service;

import by.training.finalp.entity.Order;
import by.training.finalp.exception.ServiceException;

public interface OrderService extends Service {

    Order findById(final Integer id) throws ServiceException;

    void save(final Order order) throws ServiceException;

    void delete(final Integer id) throws ServiceException;

}
