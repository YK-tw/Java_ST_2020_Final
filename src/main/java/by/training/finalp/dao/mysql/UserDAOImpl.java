package by.training.finalp.dao.mysql;

import by.training.finalp.builder.UserBuilder;
import by.training.finalp.entity.Role;
import by.training.finalp.entity.User;
import by.training.finalp.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl extends BaseDAO implements UserDAO {

    private static final Logger logger = LogManager.getLogger(UserDAOImpl.class);

    @Override
    public Integer create(final User entity) throws DAOException {
        String sql = "INSERT INTO user (login, password, role, name, surname, mail, phone, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getPassword());
            statement.setInt(3, entity.getRole().getIdentity());
            statement.setString(4, entity.getName());
            statement.setString(5, entity.getSurname());
            statement.setString(6, entity.getMail());
            statement.setString(7, entity.getPhone());
            statement.setString(8, entity.getAddress());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                logger.error("There is no autoincremented index after trying to add record into table `user`");
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
    public User read(final Integer id) throws DAOException {
        String sql = "SELECT login, password, role, name, surname, mail, phone, address FROM user WHERE id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        UserBuilder builder = new UserBuilder();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                builder.withId(id);
                builder.withLogin(resultSet.getString("login"));
                builder.withPassword(resultSet.getString("password"));
                builder.withRole(Role.getByIdentity(resultSet.getInt("role")));
                builder.withName(resultSet.getString("name"));
                builder.withSurname(resultSet.getString("surname"));
                builder.withMail(resultSet.getString("mail"));
                builder.withPhone(resultSet.getString("phone"));
                builder.withAddress(resultSet.getString("address"));
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
    public void update(final User entity) throws DAOException {
        String sql = "UPDATE user SET login = ?, password = ?, role = ?, name = ?, surname = ?, mail = ?, phone = ?, address = ? WHERE id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getPassword());
            statement.setInt(3, entity.getRole().getIdentity());
            statement.setString(4, entity.getName());
            statement.setString(5, entity.getSurname());
            statement.setString(6, entity.getMail());
            statement.setString(7, entity.getPhone());
            statement.setString(8, entity.getAddress());
            statement.setInt(9, entity.getId());
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
        String sql = "DELETE FROM user WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public User read(final String login, final String password) throws DAOException {
        String sql = "SELECT id, role FROM user WHERE login = ? AND password = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, login);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setLogin(login);
                user.setPassword(password);
                user.setRole(Role.getByIdentity(resultSet.getInt("role")));
            }
            return user;
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
    public List<User> readAll() throws DAOException {
        String sql = "SELECT * FROM user";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        UserBuilder builder;
        List<User> users = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                builder = new UserBuilder();
                builder.withId(resultSet.getInt("id"));
                builder.withLogin(resultSet.getString("login"));
                builder.withPassword(resultSet.getString("password"));
                builder.withRole(Role.getByIdentity(resultSet.getInt("role")));
                builder.withName(resultSet.getString("name"));
                builder.withSurname(resultSet.getString("surname"));
                builder.withMail(resultSet.getString("mail"));
                builder.withPhone(resultSet.getString("phone"));
                builder.withAddress(resultSet.getString("address"));
                users.add(builder.build());
            }
            return users;
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
