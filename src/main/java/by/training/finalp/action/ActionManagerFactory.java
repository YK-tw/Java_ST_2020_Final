package by.training.finalp.action;

import by.training.finalp.service.ServiceFactory;

public class ActionManagerFactory {
    public static ActionManager getManager(ServiceFactory factory) {
        return new ActionManagerImpl(factory);
    }
}
