package by.training.finalp.action;


import by.training.finalp.entity.Role;
import by.training.finalp.entity.User;
import by.training.finalp.exception.ServiceException;
import by.training.finalp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LoginAction extends Action {
    private static Logger logger = LogManager.getLogger(LoginAction.class);

    private static Map<Role, List<MenuItem>> menu = new ConcurrentHashMap<>();

    static {
        menu.put(Role.ADMINISTRATOR, new ArrayList<>(Arrays.asList(
                new MenuItem("/", "main page")
        )));
        menu.put(Role.MODERATOR, new ArrayList<>(Arrays.asList(
                new MenuItem("/", "main page")
        )));
        menu.put(Role.GUEST, new ArrayList<>(Arrays.asList(
                new MenuItem("/", "main page")
        )));
    }

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }

    @Override
    public Action.Forward exec(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login != null && password != null) {
            UserService service = factory.getService(UserService.class);
            User user = service.findByLoginAndPassword(login, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("authorizedUser", user);
                logger.info(String.format("user \"%s\" is logged in from %s (%s:%s)", login, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
                return new Forward("/login.html");
            } else {
                request.setAttribute("message", "Имя пользователя или пароль не опознанны");
                logger.info(String.format("user \"%s\" unsuccessfully tried to log in from %s (%s:%s)", login, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
            }
        }
        request.getSession().setAttribute("users", factory.getService(UserService.class).getAll());
        return null;
    }
}
