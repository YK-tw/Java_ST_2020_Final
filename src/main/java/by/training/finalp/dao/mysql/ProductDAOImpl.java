package by.training.finalp.dao.mysql;

import by.training.finalp.builder.ProductBuilder;
import by.training.finalp.entity.Attribute;
import by.training.finalp.entity.Product;
import by.training.finalp.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl extends BaseDAO implements ProductDAO {

    private static final Logger logger = LogManager.getLogger(ProductDAOImpl.class);

    @Override
    public Integer create(final Product entity) throws DAOException {
        String sql = "INSERT INTO product (name, price, existence, description, visibility) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getName());
            statement.setDouble(2, entity.getPrice());
            statement.setBoolean(3, entity.getExistence());
            statement.setString(4, entity.getDescription());
            statement.setBoolean(5, entity.getVisibility());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                logger.error("There is no autoincremented index after trying to add record into table `product`");
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
    public Product read(final Integer id) throws DAOException {
        String sql = "SELECT name, price, existence, description, visibility FROM product WHERE id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ProductBuilder builder = new ProductBuilder();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                builder.withId(id);
                builder.withName(resultSet.getString("name"));
                builder.withPrice(resultSet.getDouble("price"));
                builder.withExistence(resultSet.getBoolean("existence"));
                builder.withDescription(resultSet.getString("description"));
                builder.withVisibility(resultSet.getBoolean("visibility"));
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
    public void update(final Product entity) throws DAOException {
        String sql = "UPDATE product SET name = ?, price = ?, existence = ?, description = ?, visibility = ? WHERE id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, entity.getName());
            statement.setDouble(2, entity.getPrice());
            statement.setBoolean(3, entity.getExistence());
            statement.setString(4, entity.getDescription());
            statement.setBoolean(5, entity.getVisibility());
            statement.setInt(6, entity.getId());
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
        String sql = "DELETE FROM product WHERE id = ?";
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

    public List<Integer> getAttributesList(final Product product) throws DAOException {
        String sql = "SELECT attribute_id FROM product_attribute WHERE product_id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Integer> attributeIds = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, product.getId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                attributeIds.add(resultSet.getInt("attribute_id"));
            }
            return attributeIds;
        } catch (SQLException e) {
            throw new DAOException();
        } finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    public void addAttributes(final Product product) throws DAOException {

        String deleteSQL = "DELETE FROM product_attribute WHERE product_id = ?";
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = connection.prepareStatement(deleteSQL);
            deleteStatement.setInt(1, product.getId());
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                deleteStatement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }

        String sql = "INSERT INTO product_attribute (attribute_id, product_id) VALUES (?, ?)"; //TODO primary key on two keys
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            for (Attribute attribute : product.getAttributes()) {
                statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, attribute.getId());
                statement.setInt(2, product.getId());
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

    public List<Product> getProductsByAttributeId(Integer id) throws DAOException {
        String sql = "SELECT product_id FROM product_attribute WHERE attribute_id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Product> products = new ArrayList<>();
        try {
            List<Integer> productIds = new ArrayList<>();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                products.add(read(resultSet.getInt("product_id")));
            }
            return products;
        } catch (SQLException e) {
            throw new DAOException();
        } finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    @Override
    public List<Product> readFromTo(Integer from, Integer to) throws DAOException {
        String sql = "SELECT id, name, price, existence, description, visibility FROM product LIMIT ?, ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ProductBuilder builder;
        List<Product> products = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, from);
            statement.setInt(2, to);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                builder = new ProductBuilder();
                builder.withId(resultSet.getInt("id"));
                builder.withName(resultSet.getString("name"));
                builder.withPrice(resultSet.getDouble("price"));
                builder.withExistence(resultSet.getBoolean("existence"));
                builder.withDescription(resultSet.getString("description"));
                builder.withVisibility(resultSet.getBoolean("visibility"));
                products.add(builder.build());
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
            }
        }
    }

    @Override
    public Integer readAmount() throws DAOException {
        String sql = "SELECT COUNT(id) FROM product";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer res = 0;
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                res = resultSet.getInt(1);
            }
            return res;
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
