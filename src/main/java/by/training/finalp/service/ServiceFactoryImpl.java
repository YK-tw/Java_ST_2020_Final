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
    @SuppressWarnings("unchecked")
    public <Type extends Service> Type getService(Class<Type> key) throws ServiceException {
        Class<? extends ServiceImpl> value = SERVICES.get(key);
        if (value != null) {
            try {
                Transaction transaction = factory.createTransaction();
                ServiceImpl service = value.newInstance();
                service.setTransaction(transaction);
                return (Type) service;
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error("It is impossible to instance service class", e);
                throw new ServiceException(e);
            }
        }
        return null;
    }

    @Override
    public void close() {
        factory.close();
    }
}
