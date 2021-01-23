package by.training.finalp.controller;


import by.training.finalp.action.Action;
import by.training.finalp.action.ActionManager;
import by.training.finalp.action.ActionManagerFactory;
import by.training.finalp.dao.TransactionFactoryImpl;
import by.training.finalp.dao.connection.ConnectionPool;
import by.training.finalp.exception.DAOException;
import by.training.finalp.exception.ServiceException;
import by.training.finalp.service.ServiceFactory;
import by.training.finalp.service.ServiceFactoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {
    private static Logger logger = LogManager.getLogger(DispatcherServlet.class);

    public static final String DB_URL = "jdbc:mysql://localhost:3306/poynac_website?useUnicode=true&serverTimezone=UTC&characterEncoding=UTF-8";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "root";
    public static final int DB_POOL_START_SIZE = 10;
    public static final int DB_POOL_MAX_SIZE = 1000;
    public static final int DB_POOL_CHECK_CONNECTION_TIMEOUT = 0;

    public void init() {
        try {
            ConnectionPool.getInstance().init(DB_URL, DB_USER, DB_PASSWORD, DB_POOL_START_SIZE, DB_POOL_MAX_SIZE, DB_POOL_CHECK_CONNECTION_TIMEOUT);
        } catch (DAOException e) {
            logger.error("It is impossible to initialize application", e);
            destroy();
        }
    }

    public ServiceFactory getFactory() throws DAOException {
        return new ServiceFactoryImpl(new TransactionFactoryImpl());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Action action = (Action) request.getAttribute("action");
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                @SuppressWarnings("unchecked")
                Map<String, Object> attributes = (Map<String, Object>) session.getAttribute("redirectedData");
                if (attributes != null) {
                    for (String key : attributes.keySet()) {
                        request.setAttribute(key, attributes.get(key));
                    }
                    session.removeAttribute("redirectedData");
                }
            }
            ActionManager actionManager = ActionManagerFactory.getManager(getFactory());
            Action.Forward forward = actionManager.execute(action, request, response);
            actionManager.close();
            if (session != null && forward != null && !forward.getAttributes().isEmpty()) {
                session.setAttribute("redirectedData", forward.getAttributes());
            }
            String requestedUri = request.getRequestURI();
            if (forward != null && forward.isRedirect()) {
                String redirectedUri = request.getContextPath() + forward.getForward();
                logger.debug(String.format("Request for URI \"%s\" id redirected to URI \"%s\"", requestedUri, redirectedUri));
                response.sendRedirect(redirectedUri);
            } else {
                String jspPage;
                if (forward != null) {
                    jspPage = forward.getForward();
                } else {
                    jspPage = action.getName() + ".jsp";
                }
                jspPage = "/WEB-INF/jsp" + jspPage;
                logger.debug(String.format("Request for URI \"%s\" is forwarded to JSP \"%s\"", requestedUri, jspPage));
                getServletContext().getRequestDispatcher(jspPage).forward(request, response);
            }
        } catch (DAOException | ServiceException e) {
            logger.error("It is impossible to process request", e);
            request.setAttribute("error", "Ошибка обработки данных");
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }
}
