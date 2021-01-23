package by.training.finalp.dao.mysql;

import by.training.finalp.builder.OrderBuilder;
import by.training.finalp.entity.Order;
import by.training.finalp.entity.Product;
import by.training.finalp.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class OrderDAOImpl extends BaseDAO implements OrderDAO {

    private static final Logger logger = LogManager.getLogger(OrderDAOImpl.class);

    @Override
    public Integer create(final Order entity) throws DAOException {
        String sql = "INSERT INTO `order` (user_id, date, price) VALUES (?, ?, ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, entity.getUserId());
            statement.setDate(2, (Date) entity.getDate());
            statement.setDouble(3, entity.getPrice());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                logger.error("There is no autoincremented index after trying to add record into table `order`");
                throw new DAOException();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    @Override
    public Order read(final Integer id) throws DAOException {
        String sql = "SELECT id, user_id, date, price FROM `order` WHERE id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        OrderBuilder builder = new OrderBuilder();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                builder.withId(id);
                builder.withUserId(resultSet.getInt("user_id"));
                builder.withDate(resultSet.getDate("date"));
                builder.withPrice(resultSet.getDouble("price"));
            }
            return builder.build();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }

    }

    @Override
    public void update(final Order entity) throws DAOException {
        String sql = "UPDATE order SET user_id = ?, date = ?, price = ? WHERE id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, entity.getUserId());
            statement.setDate(2, (Date) entity.getDate());
            statement.setDouble(3, entity.getPrice());
            statement.setInt(4, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    @Override
    public void delete(final Integer id) throws DAOException {
        String sql = "DELETE FROM order WHERE id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    public Map<Integer, Integer> readProducts(final Order order) throws DAOException {

        String productsSQL = "SELECT product_id, amount FROM order_product WHERE order_id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(productsSQL);
            statement.setInt(1, order.getId());
            resultSet = statement.executeQuery();
            Map<Integer, Integer> products = new HashMap<>();
            while (resultSet.next()) {
                products.put(resultSet.getInt("product_id"), resultSet.getInt("amount"));
            }
            return products;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
                throw new DAOException(e);
            }
        }
    }

    public void addProducts(final Order order) throws DAOException {
        String sql = "INSERT INTO order_product (order_id, product_id, amount) VALUES (?,?,?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            for (Product product : order.getProducts().keySet()) {
                statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, order.getId());
                statement.setInt(2, product.getId());
                statement.setInt(3, order.getProducts().get(product));
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }
}
