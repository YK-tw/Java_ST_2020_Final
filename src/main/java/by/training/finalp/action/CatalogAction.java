package by.training.finalp.action;

import by.training.finalp.entity.Attribute;
import by.training.finalp.entity.Product;
import by.training.finalp.exception.IncorrectFormDataException;
import by.training.finalp.exception.ServiceException;
import by.training.finalp.service.AttributeService;
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
        Integer productsAmount;

        String attribute = checkAttributes(request);

        if (attribute != null && !attribute.equals("")) {
            List<Product> products = service.findByAttribute(attribute);
            List<Product> productList = new ArrayList<>();

            productsAmount = products.size();
            pageAmount = countPagesAmount(request, productsAmount, pageSize);

            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            } else if (Integer.parseInt(request.getParameter("page")) >= pageAmount) {
                page = pageAmount;
            } else {
                page = 1;
            }
            request.setAttribute("page", page);

            for (int i = (page - 1) * pageSize; (i < page * pageSize) && (i < products.size()); i++) {
                productList.add(products.get(i));
            }
            request.setAttribute("products", productList);
            request.setAttribute("attribute", attribute);
        } else {

            productsAmount = service.readAmount();
            pageAmount = countPagesAmount(request, productsAmount, pageSize);

            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            } else if (Integer.parseInt(request.getParameter("page")) >= pageAmount) {
                page = pageAmount;
            } else {
                page = 1;
            }
            request.setAttribute("page", page);

            request.setAttribute("products", service.readFromTo((page - 1) * pageSize, pageSize));
        }
        return forward;

    }

    private Integer countPagesAmount(HttpServletRequest request, Integer productsAmount, Integer pageSize) {
        Integer pageAmount;
        if (productsAmount % pageSize > 0) {
            pageAmount = productsAmount / pageSize + 1;
        } else {
            pageAmount = productsAmount / pageSize;
        }
        request.getSession().setAttribute("pages", pageAmount);
        return pageAmount;
    }

    private String checkAttributes(HttpServletRequest request) {
        String attribute = request.getParameter("attribute");
        if (attribute != null && !attribute.equals("")) {
            return attribute;
        }
        if (request.getParameter("cupronickel") != null && !request.getParameter("cupronickel").equals("")) {
            attribute = "cupronickel";
            return attribute;
        }
        if (request.getParameter("nickelsilver") != null && !request.getParameter("nickelsilver").equals("")) {
            attribute = "nickelsilver";
            return attribute;
        }
        if (request.getParameter("brass") != null && !request.getParameter("brass").equals("")) {
            attribute = "brass";
            return attribute;
        }
        if (request.getParameter("stone") != null && !request.getParameter("stone").equals("")) {
            attribute = "stone";
            return attribute;
        }
        if (request.getParameter("wood") != null && !request.getParameter("wood").equals("")) {
            attribute = "wood";
            return attribute;
        }
        if (request.getParameter("pearl") != null && !request.getParameter("pearl").equals("")) {
            attribute = "pearl";
            return attribute;
        }

        if (request.getParameter("earrings") != null && !request.getParameter("earrings").equals("")) {
            attribute = "earrings";
            return attribute;
        }
        if (request.getParameter("ring") != null && !request.getParameter("ring").equals("")) {
            attribute = "ring";
            return attribute;
        }
        if (request.getParameter("bracelet") != null && !request.getParameter("bracelet").equals("")) {
            attribute = "bracelet";
            return attribute;
        }
        if (request.getParameter("cuff") != null && !request.getParameter("cuff").equals("")) {
            attribute = "cuff";
            return attribute;
        }
        if (request.getParameter("chain") != null && !request.getParameter("chain").equals("")) {
            attribute = "chain";
            return attribute;
        }
        if (request.getParameter("pendant") != null && !request.getParameter("pendant").equals("")) {
            attribute = "pendant";
            return attribute;
        }
        return "";
    }
}
