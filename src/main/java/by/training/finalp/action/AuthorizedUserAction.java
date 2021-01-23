package by.training.finalp.action;

import by.training.finalp.entity.Role;

import java.util.Arrays;

public abstract class AuthorizedUserAction extends Action {
    public AuthorizedUserAction() {
        getAllowRoles().addAll(Arrays.asList(Role.values()));
    }
}
