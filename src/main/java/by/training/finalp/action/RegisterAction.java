package by.training.finalp.action;

import by.training.finalp.builder.UserBuilder;
import by.training.finalp.entity.Role;
import by.training.finalp.entity.User;
import by.training.finalp.exception.ServiceException;
import by.training.finalp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterAction extends Action {
    private static Logger logger = LogManager.getLogger(RegisterAction.class);

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (login != null && password != null) {
            UserService service = factory.getService(UserService.class);
            User user = new UserBuilder()
                    .withLogin(login)
                    .withPassword(password)
                    .withRole(Role.GUEST)
                    .build();
            if (user != null) {
                service.save(user);
                logger.info(String.format("user \"%s\" is registered in from %s (%s:%s)", login, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
                return new Forward("/login.html");
            } else {
                request.setAttribute("message", "Имя пользователя или пароль не опознанны");
                logger.info(String.format("user \"%s\" unsuccessfully tried to register from %s (%s:%s)", login, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
            }
        }
        return null;
    }
}
