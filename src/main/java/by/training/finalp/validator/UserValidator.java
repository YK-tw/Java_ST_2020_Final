package by.training.finalp.validator;

import by.training.finalp.entity.User;
import by.training.finalp.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public class UserValidator implements Validator<User> {
    @Override
    public User validate(HttpServletRequest request) throws IncorrectFormDataException {
        return null;
    }
}
