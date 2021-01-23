package by.training.finalp.validator;


import by.training.finalp.entity.Entity;
import by.training.finalp.entity.Product;
import by.training.finalp.entity.User;

import java.util.HashMap;
import java.util.Map;

public class ValidatorFactory {
    private static Map<Class<? extends Entity>, Class<? extends Validator<?>>> validators = new HashMap<>();

    static {
        validators.put(Product.class, ProductValidator.class);
        validators.put(User.class, UserValidator.class);
    }

    @SuppressWarnings("unchecked")
    public static <Type extends Entity> Validator<Type> createValidator(Class<Type> entity) {
        try {
            return (Validator<Type>) validators.get(entity).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }
}
