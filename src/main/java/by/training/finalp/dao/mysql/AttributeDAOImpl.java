package by.training.finalp.dao.mysql;


import by.training.finalp.builder.AttributeBuilder;
import by.training.finalp.entity.Attribute;
import by.training.finalp.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AttributeDAOImpl extends BaseDAO implements AttributeDAO {

    private static final Logger logger = LogManager.getLogger(AttributeDAOImpl.class);

    @Override
    public Integer create(final Attribute entity) throws DAOException {
        String sql = "INSERT INTO attribute (name, value) VALUES (?, ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getValue());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                logger.error("There is no autoincremented index after trying to add record into table `attribute`");
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
    public Attribute read(final Integer id) throws DAOException {
        String sql = "SELECT name, value FROM attribute WHERE id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        AttributeBuilder builder = new AttributeBuilder();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                builder.withId(id);
                builder.withName(resultSet.getString("name"));
                builder.withValue(resultSet.getString("value"));
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
    public void update(final Attribute entity) throws DAOException {
        String sql = "UPDATE attribute SET name = ?, value = ? WHERE id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getValue());
            statement.setInt(3, entity.getId());
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
        String sql = "DELETE FROM attribute WHERE id = ?";
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

    @Override
    public Integer findIdByName(String name) throws DAOException {
        String sql = "SELECT id FROM attribute WHERE name = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer result = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt("id");
            }
            return result;
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
    public Integer findIdByValue(String value) throws DAOException {
        String sql = "SELECT id FROM attribute WHERE value = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer result = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, value);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt("id");
            }
            return result;
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
    public List<Attribute> getAll() throws DAOException {
        String sql = "SELECT * FROM attribute";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        AttributeBuilder builder = new AttributeBuilder();
        List<Attribute> attributes = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                builder = new AttributeBuilder();
                builder.withId(resultSet.getInt("id"));
                builder.withName(resultSet.getString("name"));
                builder.withValue(resultSet.getString("value"));
                attributes.add(builder.build());
            }
            return attributes;
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
