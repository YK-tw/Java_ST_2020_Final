package by.training.finalp.action;

import by.training.finalp.entity.Product;
import by.training.finalp.exception.ServiceException;
import by.training.finalp.service.ProductService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CatalogAction extends AuthorizedUserAction {
    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        Forward forward = new Forward("/catalog.jsp", false);
        String attribute = request.getParameter("attribute");
        Integer page;
        Integer pageSize = 12;
        if (request.getCookies() != null) {
            for (Cookie item : request.getCookies()) {
                if (item.getName().equals("size")) {
                    pageSize = Integer.parseInt(item.getValue());
                    break;
                }
            }
        }

        ProductService service = factory.getService(ProductService.class);
        Integer pageAmount;
        Integer productsAmount = service.readAmount();
        if (productsAmount % pageSize > 0) {
            pageAmount = productsAmount / pageSize + 1;
        } else {
            pageAmount = productsAmount / pageSize;
        }
        request.getSession().setAttribute("pages", pageAmount);
        if (request.getParameter("page") != null && Integer.parseInt(request.getParameter("page")) <= pageAmount) {
            page = Integer.parseInt(request.getParameter("page"));
        } else {
            page = 1;
        }
        request.setAttribute("page", page);
        if (attribute != null && !attribute.equals("")) {
            List<Product> products = service.findByAttribute(attribute);
            List<Product> productList = new ArrayList<>();

            for (int i = (page - 1) * pageSize; (i < page * pageSize) && (i < products.size()); i++) {
                productList.add(products.get(i));
            }
            request.setAttribute("products", productList);
        } else {
            request.setAttribute("products", service.readFromTo((page - 1) * pageSize, pageSize));
        }
        return forward;

    }
}
