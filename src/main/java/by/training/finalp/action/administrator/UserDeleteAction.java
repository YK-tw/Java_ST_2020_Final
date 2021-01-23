package by.training.finalp.action.administrator;

import by.training.finalp.action.Action;
import by.training.finalp.exception.ServiceException;
import by.training.finalp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserDeleteAction extends Action {
    private static Logger logger = LogManager.getLogger(UserDeleteAction.class);

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String id = request.getParameter("id");
        if (id != null) {
            UserService userService = factory.getService(UserService.class);
            Integer identity = Integer.parseInt(id);
            if (!userService.findById(identity).equals(request.getSession().getAttribute("authorizedUser"))) {
                userService.delete(identity);
            }
            logger.info(String.format("User \"%s\" deleted product with identity %d", userService.findById(identity).getLogin(), identity));
        }
        return new Forward("/index.html");
    }
}
