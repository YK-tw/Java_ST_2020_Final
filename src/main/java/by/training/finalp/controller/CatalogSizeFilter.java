package by.training.finalp.controller;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CatalogSizeFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        Cookie size = new Cookie("size", "12");
        if (req.getCookies() != null) {
            for (Cookie item : req.getCookies()) {
                if (item.getName().equals("size")) {
                    size = item;
                    break;
                }
            }
        }

        if (req.getParameter("size") != null) {
            Cookie cookie = new Cookie("size", req.getParameter("size"));
            res.addCookie(cookie);
            res.sendRedirect(res.encodeRedirectURL(req.getRequestURI())
                    + "?" + req.getQueryString().replaceAll("size=[0-9]+", ""));
            return;
        } else {
            res.addCookie(size);
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }

    public void init(FilterConfig arg0) throws ServletException {
    }

}
