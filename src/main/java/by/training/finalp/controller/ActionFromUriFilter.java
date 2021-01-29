package by.training.finalp.controller;


import by.training.finalp.action.*;
import by.training.finalp.action.administrator.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ActionFromUriFilter implements Filter {
    private static Logger logger = LogManager.getLogger(ActionFromUriFilter.class);

    private static Map<String, Class<? extends Action>> actions = new ConcurrentHashMap<>();

    static {
        actions.put("/", MainAction.class);
        actions.put("/index", MainAction.class);

        actions.put("/register", RegisterAction.class);
        actions.put("/login", LoginAction.class);
        actions.put("/logout", LogoutAction.class);

        actions.put("/catalog", CatalogAction.class);

        actions.put("/product/read", ProductReadAction.class);
        actions.put("/product/delete", ProductDeleteAction.class);
        actions.put("/product/update", ProductUpdateAction.class);
        actions.put("/product/save", ProductSaveAction.class);

        actions.put("/user/downgrade", UserRoleDowngradeAction.class);
        actions.put("/user/upgrade", UserRoleUpgradeAction.class);
        actions.put("/user/delete", UserDeleteAction.class);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String contextPath = httpRequest.getContextPath();
            String uri = httpRequest.getRequestURI();
            logger.debug(String.format("Starting of processing of request for URI \"%s\"", uri));
            int beginAction = contextPath.length();
            int endAction = uri.lastIndexOf('.');
            String actionName;
            if (endAction >= 0) {
                actionName = uri.substring(beginAction, endAction);
            } else {
                actionName = uri.substring(beginAction);
            }
            Class<? extends Action> actionClass = actions.get(actionName);

            try {
                Action action;

                if (actionClass.equals(MainAction.class)) {
                    action = new MainAction();
                } else if (actionClass.equals(RegisterAction.class)) {
                    action = new RegisterAction();
                } else if (actionClass.equals(LoginAction.class)) {
                    action = new LoginAction();
                } else if (actionClass.equals(LogoutAction.class)) {
                    action = new LogoutAction();
                } else if (actionClass.equals(CatalogAction.class)) {
                    action = new CatalogAction();
                } else if (actionClass.equals(ProductReadAction.class)) {
                    action = new ProductReadAction();
                } else if (actionClass.equals(ProductDeleteAction.class)) {
                    action = new ProductDeleteAction();
                } else if (actionClass.equals(ProductUpdateAction.class)) {
                    action = new ProductUpdateAction();
                } else if (actionClass.equals(ProductSaveAction.class)) {
                    action = new ProductSaveAction();
                } else if (actionClass.equals(UserRoleUpgradeAction.class)) {
                    action = new UserRoleUpgradeAction();
                } else if (actionClass.equals(UserRoleDowngradeAction.class)) {
                    action = new UserRoleDowngradeAction();
                } else {
                    action = new UserDeleteAction();
                }

                action.setName(actionName);
                httpRequest.setAttribute("action", action);
                chain.doFilter(request, response);
            } catch (NullPointerException e) {
                logger.error("It is impossible to create action handler object", e);
                httpRequest.setAttribute("error", String.format("Запрошенный адрес %s не может быть обработан сервером", uri));
                httpRequest.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
            }
        } else {
            logger.error("It is impossible to use HTTP filter");
            request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}

