package service;

import by.training.finalp.builder.AttributeBuilder;
import by.training.finalp.builder.UserBuilder;
import by.training.finalp.dao.TransactionFactoryImpl;
import by.training.finalp.dao.connection.ConnectionPool;
import by.training.finalp.entity.Attribute;
import by.training.finalp.entity.Role;
import by.training.finalp.entity.User;
import by.training.finalp.exception.ServiceException;
import by.training.finalp.service.AttributeService;
import by.training.finalp.service.ServiceFactoryImpl;
import by.training.finalp.service.UserService;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class AttributeServiceTest {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/test?useUnicode=true&serverTimezone=UTC&characterEncoding=UTF-8";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "root";
    public static final int DB_POOL_START_SIZE = 10;
    public static final int DB_POOL_MAX_SIZE = 1000;
    public static final int DB_POOL_CHECK_CONNECTION_TIMEOUT = 0;

    private AttributeService service;

    @BeforeClass
    public void init() {
        try {
            ConnectionPool.getInstance().init(DB_URL, DB_USER, DB_PASSWORD, DB_POOL_START_SIZE, DB_POOL_MAX_SIZE, DB_POOL_CHECK_CONNECTION_TIMEOUT);
            service = new ServiceFactoryImpl(new TransactionFactoryImpl()).getService(AttributeService.class);
        } catch (Exception e) {
        }
    }

    @DataProvider(name = "read")
    public Object[][] readProviderMethod() {
        return new Object[][]{
                {1, 1},
                {2, 2},
                {-1, null},
                {0, null},
                {350000, null}
        };
    }

    @Test(dataProvider = "read")
    public void testReadById(Integer id, Integer expected) {
        try {
            assertEquals(service.findById(id).getId(), expected);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @DataProvider(name = "save")
    public Object[][] saveProviderMethod() {

        return new Object[][]{
                {new Attribute(), 11},
                {new AttributeBuilder().withName("name").withValue("value").build(), 12},
                {((AttributeBuilder) new AttributeBuilder().withName("name").withValue("value").withId(1)).build(), 12}
        };
    }

    @Test(dataProvider = "save")
    public void testSave(Attribute attribute, Integer amount) {
        try {
            service.save(attribute);
            Integer size = service.getAll().size();
            assertEquals(size, amount);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @DataProvider(name = "delete")
    public Object[][] deleteProviderMethod() {
        return new Object[][]{
                {1, 11},
                {2, 11},
                {1, 11}
        };
    }

    @Test(dataProvider = "delete")
    public void testDelete(Integer id, Integer amount) {
        try {
            service.delete(id);
            Integer size = service.getAll().size();
            assertEquals(size, amount);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

}
