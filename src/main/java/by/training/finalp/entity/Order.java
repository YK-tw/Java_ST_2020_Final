package by.training.finalp.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Order extends Entity {
    private Integer userId;
    private Date date;
    private Double price;
    /**
     * Products in order.
     * Key - product, value - product amount in order.
     */
    private Map<Product, Integer> products;

    /**
     * UserId getter.
     *
     * @return Integer userId.
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * UserId setter.
     *
     * @param gotUserId Integer userId.
     */
    public void setUserId(final Integer gotUserId) {
        this.userId = gotUserId;
    }

    /**
     * Date setter.
     *
     * @return Date date.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Date setter.
     *
     * @param gotDate Date date.
     */
    public void setDate(final Date gotDate) {
        this.date = gotDate;
    }

    /**
     * Price getter.
     *
     * @return Double price.
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Price setter.
     *
     * @param gotPrice Double price.
     */
    public void setPrice(final Double gotPrice) {
        this.price = gotPrice;
    }

    /**
     * Products getter.
     *
     * @return Map<Product, Integer> products.
     */
    public Map<Product, Integer> getProducts() {
        return products;
    }

    /**
     * Products setter.
     *
     * @param gotProducts Map<Product, Integer> products.
     */
    public void setProducts(final Map<Product, Integer> gotProducts) {
        products = new HashMap<>();
        products.putAll(gotProducts);
    }
}
