package by.training.finalp.builder;

import by.training.finalp.entity.Role;
import by.training.finalp.entity.User;

public class UserBuilder extends Builder {

    public UserBuilder() {
        entity = new User();
    }

    public UserBuilder withLogin(final String login) {
        ((User) entity).setLogin(login);
        return this;
    }

    public UserBuilder withPassword(final String password) {
        ((User) entity).setPassword(password);
        return this;
    }

    public UserBuilder withRole(final Role role) {
        ((User) entity).setRole(role);
        return this;
    }

    public UserBuilder withName(final String name) {
        ((User) entity).setName(name);
        return this;
    }

    public UserBuilder withSurname(final String surname) {
        ((User) entity).setSurname(surname);
        return this;
    }

    public UserBuilder withMail(final String mail) {
        ((User) entity).setMail(mail);
        return this;
    }

    public UserBuilder withPhone(final String phone) {
        ((User) entity).setPhone(phone);
        return this;
    }

    public UserBuilder withAddress(final String address) {
        ((User) entity).setAddress(address);
        return this;
    }

    public User build() {
        return ((User) entity);
    }
}
