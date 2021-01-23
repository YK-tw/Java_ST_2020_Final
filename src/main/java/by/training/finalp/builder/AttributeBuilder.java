package by.training.finalp.builder;

import by.training.finalp.entity.Attribute;

public class AttributeBuilder extends Builder {

    public AttributeBuilder() {
        entity = new Attribute();
    }

    public AttributeBuilder withName(final String name) {
        ((Attribute) entity).setName(name);
        return this;
    }

    public AttributeBuilder withValue(final String value) {
        ((Attribute) entity).setValue(value);
        return this;
    }

    public Attribute build() {
        return (Attribute) entity;
    }

}
