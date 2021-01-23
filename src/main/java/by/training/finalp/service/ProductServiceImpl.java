package by.training.finalp.service;

import by.training.finalp.dao.mysql.AttributeDAO;
import by.training.finalp.dao.mysql.ProductDAO;
import by.training.finalp.entity.Attribute;
import by.training.finalp.entity.Product;
import by.training.finalp.exception.DAOException;
import by.training.finalp.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl extends ServiceImpl implements ProductService {
    @Override
    public Product findById(final Integer id) throws ServiceException {
        try {
            ProductDAO dao = transaction.createDao(ProductDAO.class);
            return buildProduct(dao.read(id));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void save(final Product product) throws ServiceException {
        try {
            ProductDAO dao = transaction.createDao(ProductDAO.class);
            if (product.getId() != null) {
                dao.update(product);
                dao.addAttributes(product);
            } else {
                product.setId(dao.create(product));
                dao.addAttributes(product);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(final Integer id) throws ServiceException {
        try {
            ProductDAO dao = transaction.createDao(ProductDAO.class);
            dao.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public List<Product> findByAttribute(final String attribute) throws ServiceException {
        List<Product> products = new ArrayList<>();
        try {
            ProductDAO productDAO = transaction.createDao(ProductDAO.class);
            AttributeDAO attributeDAO = transaction.createDao(AttributeDAO.class);

            Integer attrId = attributeDAO.findIdByValue(attribute);
            if (attrId != null) {
                products = productDAO.getProductsByAttributeId(attrId);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return products;
    }

    public List<Product> readFromTo(Integer from, Integer to) throws ServiceException {
        List<Product> products = new ArrayList<>();
        try {
            ProductDAO productDAO = transaction.createDao(ProductDAO.class);
            products = productDAO.readFromTo(from, to);


        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return products;
    }

    private Product buildProduct(final Product product) throws ServiceException {
        try {
            ProductDAO productDAO = transaction.createDao(ProductDAO.class);
            AttributeDAO attributeDAO = transaction.createDao(AttributeDAO.class);
            List<Integer> attributeIds = productDAO.getAttributesList(product);
            List<Attribute> attributes = new ArrayList<>();
            for (Integer attrId : attributeIds) {
                attributes.add(attributeDAO.read(attrId));
            }
            product.setAttributes(attributes);
            return product;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


}
