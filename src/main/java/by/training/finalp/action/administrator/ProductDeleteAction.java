package by.training.finalp.action.administrator;

import by.training.finalp.entity.User;
import by.training.finalp.exception.ServiceException;
import by.training.finalp.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ProductDeleteAction extends AdministratorAction {
    private static Logger logger = LogManager.getLogger(ProductDeleteAction.class);

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        Forward forward = new Forward("/catalog.html?page=1");
        try {
            ProductService service = factory.getService(ProductService.class);
            Integer identity = Integer.parseInt(request.getParameter("id"));

            service.delete(identity);

            logger.info(String.format("User \"%s\" deleted book with identity %d", ((User) request.getSession().getAttribute("authorizedUser")).getLogin(), identity));
        } catch (NumberFormatException e) {
            logger.warn(String.format("Incorrect data was found when user \"%s\" tried to delete product", ((User) request.getSession().getAttribute("authorizedUser")).getLogin()), e);
        }
        return forward;
    }
}
