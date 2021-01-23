package by.training.finalp.dao.mysql;

import by.training.finalp.entity.Product;
import by.training.finalp.exception.DAOException;

import java.util.List;

public interface ProductDAO extends DAO<Product> {
    List<Integer> getAttributesList(final Product product) throws DAOException;

    void addAttributes(final Product product) throws DAOException;

    List<Product> getProductsByAttributeId(Integer attrId) throws DAOException;

    List<Product> readFromTo(Integer from, Integer to) throws DAOException;
}
