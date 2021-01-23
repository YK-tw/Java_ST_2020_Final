package by.training.finalp.builder;

import by.training.finalp.entity.Entity;

public abstract class Builder {
    protected Entity entity;

    public Builder withId(final Integer id) {
        entity.setId(id);
        return this;
    }

}
