package by.training.finalp.service;

import by.training.finalp.dao.mysql.OrderDAO;
import by.training.finalp.dao.mysql.ProductDAO;
import by.training.finalp.entity.Order;
import by.training.finalp.entity.Product;
import by.training.finalp.exception.DAOException;
import by.training.finalp.exception.ServiceException;

import java.util.HashMap;
import java.util.Map;

public class OrderServiceImpl extends ServiceImpl implements OrderService {
    @Override
    public Order findById(final Integer id) throws ServiceException {
        try {
            OrderDAO dao = transaction.createDao(OrderDAO.class);
            return buildOrder(dao.read(id));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void save(final Order order) throws ServiceException {
        try {
            OrderDAO dao = transaction.createDao(OrderDAO.class);
            if (order.getId() != null) {
                dao.update(order);
                dao.addProducts(order);
            } else {
                order.setId(dao.create(order));
                dao.addProducts(order);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        try {
            OrderDAO dao = transaction.createDao(OrderDAO.class);
            dao.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public Order buildOrder(final Order order) throws ServiceException {
        try {
            OrderDAO orderDAO = transaction.createDao(OrderDAO.class);
            ProductDAO productDAO = transaction.createDao(ProductDAO.class);
            Map<Integer, Integer> productsIds = orderDAO.readProducts(order);
            Map<Product, Integer> products = new HashMap<>();

            for (Integer key : productsIds.keySet()) {
                products.put(productDAO.read(key), productsIds.get(key));
            }
            order.setProducts(products);
            return order;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
