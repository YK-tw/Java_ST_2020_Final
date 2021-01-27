package service;

import by.training.finalp.builder.ProductBuilder;
import by.training.finalp.dao.TransactionFactoryImpl;
import by.training.finalp.dao.connection.ConnectionPool;
import by.training.finalp.entity.Product;
import by.training.finalp.exception.ServiceException;
import by.training.finalp.service.ProductService;
import by.training.finalp.service.ServiceFactoryImpl;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;


public class ProductServiceTest {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/test?useUnicode=true&serverTimezone=UTC&characterEncoding=UTF-8";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "root";
    public static final int DB_POOL_START_SIZE = 10;
    public static final int DB_POOL_MAX_SIZE = 1000;
    public static final int DB_POOL_CHECK_CONNECTION_TIMEOUT = 0;

    private ProductService service;

    @BeforeClass
    public void init() {
        try {
            ConnectionPool.getInstance().init(DB_URL, DB_USER, DB_PASSWORD, DB_POOL_START_SIZE, DB_POOL_MAX_SIZE, DB_POOL_CHECK_CONNECTION_TIMEOUT);
            service = new ServiceFactoryImpl(new TransactionFactoryImpl()).getService(ProductService.class);
        } catch (Exception e) {
        }
    }

    @DataProvider(name = "read")
    public Object[][] readProviderMethod() {
        return new Object[][]{
                {1},
                {2},
                {-1},
                {0},
                {350000}
        };
    }

    @Test(dataProvider = "read")
    public void testReadById(Integer id) {
        try {
            assertEquals(service.findById(id).getId(), id);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @DataProvider(name = "save")
    public Object[][] saveProviderMethod() {

        return new Object[][]{
                {new Product(), 25},
                {new ProductBuilder().withName("name").withPrice(20.0).withExistence(true).withDescription("d").withVisibility(true).build(), 25},
                {((ProductBuilder) new ProductBuilder().withName("namena").withPrice(20.0).withExistence(true).withDescription("d").withVisibility(true).withId(22)).build(), 26}
        };
    }

    @Test(dataProvider = "save")
    public void testSave(Product product, Integer amount) {
        try {
            service.save(product);
            assertEquals(service.readAmount(), amount);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @DataProvider(name = "delete")
    public Object[][] deleteProviderMethod() {
        return new Object[][]{
                {1, 23},
                {2, 22},
                {1, 22}
        };
    }

    @Test(dataProvider = "delete")
    public void testDelete(Integer id, Integer amount) {
        try {
            service.delete(id);
            assertEquals(service.readAmount(), amount);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

}
