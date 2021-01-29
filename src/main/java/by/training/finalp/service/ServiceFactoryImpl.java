package by.training.finalp.service;

import by.training.finalp.dao.Transaction;
import by.training.finalp.dao.TransactionFactory;
import by.training.finalp.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceFactoryImpl implements ServiceFactory {
    private static Logger logger = LogManager.getLogger(ServiceFactoryImpl.class);

    private static final Map<Class<? extends Service>, Class<? extends ServiceImpl>> SERVICES = new ConcurrentHashMap<>();

    static {
        SERVICES.put(UserService.class, UserServiceImpl.class);
        SERVICES.put(OrderService.class, OrderServiceImpl.class);
        SERVICES.put(ProductService.class, ProductServiceImpl.class);
        SERVICES.put(AttributeService.class, AttributeServiceImpl.class);
    }

    private TransactionFactory factory;

    public ServiceFactoryImpl(TransactionFactory factory) {
        this.factory = factory;
    }

    @Override
    public <Type extends Service> Type getService(Class<Type> key) {
        Class<? extends ServiceImpl> value = SERVICES.get(key);
        ServiceImpl service;
        if (value != null) {
            Transaction transaction = factory.createTransaction();

            if (value.equals(UserServiceImpl.class)) {
                service = new UserServiceImpl();
            } else if (value.equals(OrderServiceImpl.class)) {
                service = new OrderServiceImpl();
            } else if (value.equals(ProductServiceImpl.class)) {
                service = new ProductServiceImpl();
            } else {
                service = new AttributeServiceImpl();
            }
            service.setTransaction(transaction);

            return (Type) service;
        }
        return null;
    }

    @Override
    public void close() {
        factory.close();
    }
}
