package by.training.finalp.action.administrator;

import by.training.finalp.action.Action;
import by.training.finalp.entity.Role;
import by.training.finalp.entity.User;
import by.training.finalp.exception.ServiceException;
import by.training.finalp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserRoleDowngradeAction extends Action {
    private static Logger logger = LogManager.getLogger(UserRoleDowngradeAction.class);

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String id = request.getParameter("id");
        if (id != null) {
            UserService userService = factory.getService(UserService.class);
            Integer identity = Integer.parseInt(id);
            User user = userService.findById(identity);
            if (user.getRole().ordinal() > 0 && identity != ((User) request.getSession().getAttribute("authorizedUser")).getId()) {
                user.setRole(Role.values()[user.getRole().ordinal() - 1]);
                userService.save(user);
                logger.info(String.format("User \"%s\" role was downgraded", user.getLogin()));
            }
        }
        return new Forward("/login.html");
    }
}
