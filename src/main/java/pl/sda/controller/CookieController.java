package pl.sda.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by RENT on 2017-03-01.
 */
public class CookieController implements Controller {
    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie cookie = new Cookie("hello", "world");
        cookie.setMaxAge(1000);
        response.addCookie(cookie);

        response.getWriter().write("<h1>Hello From Cookie Controller<h1>");
    }
}
