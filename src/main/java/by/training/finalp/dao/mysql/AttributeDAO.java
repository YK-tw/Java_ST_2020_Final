package by.training.finalp.dao.mysql;

import by.training.finalp.entity.Attribute;
import by.training.finalp.exception.DAOException;

import java.util.List;

public interface AttributeDAO extends DAO<Attribute> {

    Integer findIdByName(String name) throws DAOException;

    Integer findIdByValue(String attribute) throws DAOException;

    List<Attribute> getAll() throws DAOException;
}
