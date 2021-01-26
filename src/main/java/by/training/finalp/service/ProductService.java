package by.training.finalp.service;

import by.training.finalp.entity.Product;
import by.training.finalp.exception.ServiceException;

import java.util.List;

public interface ProductService extends Service {

    Product findById(final Integer id) throws ServiceException;

    void save(final Product product) throws ServiceException;

    void delete(final Integer id) throws ServiceException;

    List<Product> findByAttribute(final String attribute) throws ServiceException;

    List<Product> readFromTo(Integer from, Integer to) throws ServiceException;

    Integer readAmount() throws ServiceException;
}
