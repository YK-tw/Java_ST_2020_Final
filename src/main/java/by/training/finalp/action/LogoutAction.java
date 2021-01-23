package by.training.finalp.action;

import by.training.finalp.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutAction extends AuthorizedUserAction {
    private static Logger logger = LogManager.getLogger(LogoutAction.class);

    @Override
    public Action.Forward exec(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("authorizedUser");
        logger.info(String.format("user \"%s\" is logged out", user.getLogin()));
        request.getSession(false).invalidate();
        return new Forward("/login.html");
    }
}
