package by.training.finalp.controller;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CookieLocaleFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        Cookie lang = new Cookie("lang", "ru");

        for (Cookie item : req.getCookies()) {
            if (item.getName().equals("lang")) {
                lang = item;
            }
        }

        if (req.getParameter("cookieLocale") != null) {
            Cookie cookie = new Cookie("lang", req.getParameter("cookieLocale"));
            res.addCookie(cookie);
            res.sendRedirect(res.encodeRedirectURL(req.getRequestURI())
                    + "?" + req.getQueryString().replaceAll("cookieLocale=[a-z]{2}", ""));
            return;
        } else {
            res.addCookie(lang);
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }

    public void init(FilterConfig arg0) throws ServletException {
    }

}