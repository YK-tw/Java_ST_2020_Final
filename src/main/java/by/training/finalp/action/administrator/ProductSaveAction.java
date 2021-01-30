package by.training.finalp.action.administrator;

import by.training.finalp.entity.Attribute;
import by.training.finalp.entity.Product;
import by.training.finalp.entity.User;
import by.training.finalp.exception.IncorrectFormDataException;
import by.training.finalp.exception.ServiceException;
import by.training.finalp.service.AttributeService;
import by.training.finalp.service.ProductService;
import by.training.finalp.service.ServiceFactory;
import by.training.finalp.validator.Validator;
import by.training.finalp.validator.ValidatorFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProductSaveAction extends AdministratorAction {
    private static Logger logger = LogManager.getLogger(ProductSaveAction.class);

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        Forward forward = new Forward("/catalog.html?page=1");
        try {
            Validator<Product> validator = ValidatorFactory.createValidator(Product.class);
            Product product = saveImage(request, validateAttributes(request, validator.validate(request), factory));
            ProductService service = factory.getService(ProductService.class);
            service.save(product);
            logger.info(String.format("User \"%s\" saved book with identity %d", ((User) request.getSession().getAttribute("authorizedUser")).getLogin(), product.getId()));
        } catch (IncorrectFormDataException e) {
            forward.getAttributes().put("message", "Были обнаружены некорректные данные");
            logger.warn(String.format("Incorrect data was found when user \"%s\" tried to save book", getAuthorizedUser().getLogin()), e);
        }
        return forward;
    }

    private Product validateAttributes(HttpServletRequest request, Product product, ServiceFactory factory) throws IncorrectFormDataException {
        String[] attributes = request.getParameterValues("attribute");
        List<Attribute> attributeList = new ArrayList<>();
        if (attributes != null) {
            try {
                AttributeService service = factory.getService(AttributeService.class);
                for (int i = 0; i < attributes.length; i++) {
                    attributeList.add(service.findById(Integer.parseInt(attributes[i])));
                }
                product.setAttributes(attributeList);
            } catch (NumberFormatException | ServiceException e) {
                throw new IncorrectFormDataException("visibility", attributes.toString());
            }
        }
        return product;
    }

    private Product saveImage(HttpServletRequest request, Product product) {
        try {
            String appPath = request.getServletContext().getRealPath("");
            appPath = appPath.replace('\\', '/');

            String fullSavePath;
            if (appPath.endsWith("/")) {
                fullSavePath = appPath + "img/main/download/";
            } else {
                fullSavePath = appPath + "/img/main/download/";
            }

            File fileSaveDir = new File(fullSavePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }

            String path = "";
            for (Part part : request.getParts()) {
                String fileName = extractFileName(part);
                if (fileName != null && fileName.length() > 0) {
                    String filePath = fullSavePath + File.separator + fileName;
                    part.write(filePath);
                    path = fileName;
                }
            }
            if (appPath != null && !appPath.equals("")) {
                product.setImgPath(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn(e.getMessage());
        }
        return product;
    }


    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                String clientFileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
                clientFileName = clientFileName.replace("\\", "/");
                int i = clientFileName.lastIndexOf('/');
                return clientFileName.substring(i + 1);
            }
        }
        return null;
    }

}
