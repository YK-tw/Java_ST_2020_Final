package by.training.finalp.action.administrator;

import by.training.finalp.entity.Attribute;
import by.training.finalp.entity.Product;
import by.training.finalp.exception.ServiceException;
import by.training.finalp.service.AttributeService;
import by.training.finalp.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ProductUpdateAction extends AdministratorAction {
    private static Logger logger = LogManager.getLogger(ProductUpdateAction.class);

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try {
            String id = request.getParameter("id");
            if (id != null) {
                Integer identity = Integer.parseInt(request.getParameter("id"));

                ProductService service = factory.getService(ProductService.class);
                Product product = service.findById(identity);
                if (product != null) {
                    request.setAttribute("product", product);
                }
            }
            AttributeService attrService = factory.getService(AttributeService.class);
            List<Attribute> attributeList = attrService.getAll();
            if (attributeList != null) {
                request.setAttribute("attributes", attributeList);
            }
        } catch (NumberFormatException e) {
            try {
                Integer selectedAuthorIdentity = Integer.parseInt(request.getParameter("authorIdentity"));
                request.setAttribute("selectedAuthorIdentity", selectedAuthorIdentity);
            } catch (NumberFormatException e1) {
                logger.warn(e1.getMessage());
            }
        }
        return null;
    }
}
