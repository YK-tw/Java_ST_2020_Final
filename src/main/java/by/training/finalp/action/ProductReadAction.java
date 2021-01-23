package by.training.finalp.action;

import by.training.finalp.entity.Product;
import by.training.finalp.exception.ServiceException;
import by.training.finalp.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProductReadAction extends AuthorizedUserAction {
    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        Forward forward = new Forward("/product/product.jsp", false);
        String attribute = request.getParameter("id");
        Integer id = -1;
        if (attribute != null) {
            id = Integer.parseInt(attribute);
        }
        ProductService service = factory.getService(ProductService.class);
        Product gotProduct = service.findById(id);
        if (gotProduct != null) {
            request.setAttribute("product", gotProduct);
        }

        return forward;
    }
}
