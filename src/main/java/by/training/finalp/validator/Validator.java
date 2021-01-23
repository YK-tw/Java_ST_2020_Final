package by.training.finalp.validator;

import by.training.finalp.entity.Entity;
import by.training.finalp.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public interface Validator<Type extends Entity> {
    Type validate(HttpServletRequest request) throws IncorrectFormDataException;
}

