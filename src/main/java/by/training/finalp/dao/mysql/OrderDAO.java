package by.training.finalp.dao.mysql;

import by.training.finalp.entity.Order;
import by.training.finalp.exception.DAOException;

import java.util.Map;

public interface OrderDAO extends DAO<Order> {
    Map<Integer, Integer> readProducts(final Order order) throws DAOException;

    void addProducts(final Order order) throws DAOException;
}
