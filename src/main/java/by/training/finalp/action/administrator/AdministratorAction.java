package by.training.finalp.action.administrator;

import by.training.finalp.action.Action;
import by.training.finalp.entity.Role;

public abstract class AdministratorAction extends Action {
    public AdministratorAction() {
        getAllowRoles().add(Role.ADMINISTRATOR);
    }
}
