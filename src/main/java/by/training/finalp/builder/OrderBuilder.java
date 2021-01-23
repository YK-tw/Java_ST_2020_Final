package by.training.finalp.builder;

import by.training.finalp.entity.Order;
import by.training.finalp.entity.Product;

import java.util.Date;
import java.util.Map;

public class OrderBuilder extends Builder {

    public OrderBuilder() {
        entity = new Order();
    }

    public OrderBuilder withUserId(final Integer userId) {
        ((Order) entity).setUserId(userId);
        return this;
    }

    public OrderBuilder withDate(final Date date) {
        ((Order) entity).setDate(date);
        return this;
    }

    public OrderBuilder withPrice(final Double price) {
        ((Order) entity).setPrice(price);
        return this;
    }

    public OrderBuilder withProducts(final Map<Product, Integer> products) {
        ((Order) entity).setProducts(products);
        return this;
    }

    public Order build() {
        return (Order) entity;
    }

}
