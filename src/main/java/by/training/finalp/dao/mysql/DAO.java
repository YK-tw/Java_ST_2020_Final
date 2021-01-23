package by.training.finalp.dao.mysql;

import by.training.finalp.entity.Entity;
import by.training.finalp.exception.DAOException;

public interface DAO<Type extends Entity> {

    Integer create(final Type entity) throws DAOException;

    Type read(final Integer id) throws DAOException;

    void update(final Type entity) throws DAOException;

    void delete(final Integer id) throws DAOException;

}
