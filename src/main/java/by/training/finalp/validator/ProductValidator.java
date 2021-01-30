package by.training.finalp.validator;

import by.training.finalp.builder.ProductBuilder;
import by.training.finalp.entity.Product;
import by.training.finalp.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public class ProductValidator implements Validator<Product> {
    @Override
    public Product validate(HttpServletRequest request) throws IncorrectFormDataException {
        ProductBuilder builder = new ProductBuilder();
        String parameter = request.getParameter("id");
        if (parameter != null && !parameter.isEmpty()) {
            try {
                builder.withId(Integer.parseInt(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("id", parameter);
            }
        }
        parameter = request.getParameter("name");
        if (parameter != null && !parameter.isEmpty()) {
            try {
                builder.withName(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("name", parameter);
            }
        }
        parameter = request.getParameter("price");
        if (parameter != null) {
            try {
                builder.withPrice(Double.parseDouble(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("price", parameter);
            }
        }
        parameter = request.getParameter("existence");
        if (parameter != null) {
            try {
                if (parameter.equals("on")) {
                    builder.withExistence(true);
                } else {
                    builder.withExistence(false);
                }
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("existence", parameter);
            }
        }
        parameter = request.getParameter("description");
        if (parameter != null && !parameter.isEmpty()) {
            try {
                builder.withDescription(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("description", parameter);
            }
        }
        parameter = request.getParameter("visibility");
        if (parameter != null) {
            try {
                if (parameter.equals("on")) {
                    builder.withVisibility(true);
                } else {
                    builder.withVisibility(false);
                }
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("visibility", parameter);
            }
        }


        return builder.build();
    }
}
