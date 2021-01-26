package by.training.finalp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainAction extends AuthorizedUserAction {
    @Override
    public Action.Forward exec(HttpServletRequest request, HttpServletResponse response) {
        return new Forward("/main.jsp", false);
    }


}