package by.training.finalp.entity;

import java.util.ArrayList;
import java.util.List;

public class Product extends Entity {
    private String name;
    private Double price;
    private boolean existence;
    private String description;
    private boolean visibility;
    private String imgPath;
    /**
     * Ids of product attributes.
     */
    private List<Attribute> attributes;

    public String getName() {
        return name;
    }

    public void setName(final String gotName) {
        this.name = gotName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double gotPrice) {
        this.price = gotPrice;
    }

    public boolean getExistence() {
        return existence;
    }

    public void setExistence(final boolean gotExistence) {
        this.existence = gotExistence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String gotDescription) {
        this.description = gotDescription;
    }

    public boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(final boolean gotVisibility) {
        this.visibility = gotVisibility;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public String getImgPath() { return imgPath; }

    public void setImgPath(final String gotImgPath) {
        this.imgPath = gotImgPath;
    }

    public void setAttributes(List<Attribute> gotAttributes) {
        attributes = new ArrayList<>(gotAttributes);
    }
}
