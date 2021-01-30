package by.training.finalp.builder;

import by.training.finalp.entity.Attribute;
import by.training.finalp.entity.Product;

import java.util.List;

public class ProductBuilder extends Builder {

    public ProductBuilder() {
        entity = new Product();
    }

    public ProductBuilder withName(final String name) {
        ((Product) entity).setName(name);
        return this;
    }

    public ProductBuilder withPrice(final Double price) {
        ((Product) entity).setPrice(price);
        return this;
    }

    public ProductBuilder withExistence(final boolean existence) {
        ((Product) entity).setExistence(existence);
        return this;
    }

    public ProductBuilder withDescription(final String description) {
        ((Product) entity).setDescription(description);
        return this;
    }

    public ProductBuilder withVisibility(final boolean visibility) {
        ((Product) entity).setVisibility(visibility);
        return this;
    }

    public ProductBuilder withImgPath(final String imgPath) {
        ((Product) entity).setImgPath(imgPath);
        return this;
    }

    public ProductBuilder withAttributes(final List<Attribute> attributes) {
        ((Product) entity).setAttributes(attributes);
        return this;
    }

    public Product build() {
        return (Product) entity;
    }

}
