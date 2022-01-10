package edu.bbte.idde.jaim1826.web.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

@WebServlet("/login")
public class Login extends HttpServlet {
    private static final String USERNAME = "jaim1826";
    private static final String PASSWORD = "12345";
    static final Logger LOGGER = LoggerFactory.getLogger(Login.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        if (req.getSession().getAttribute("username") == null
                || req.getSession().getAttribute("password") == null) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Login failed!");
        } else {
            res.sendRedirect(req.getContextPath() + "/component");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        LOGGER.info("POST request");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (Objects.equals(username, USERNAME) && Objects.equals(password, PASSWORD)) {
            LOGGER.info("Logged in successfully!");
            req.getSession().setAttribute("username", username);
            req.getSession().setAttribute("password", password);
            res.sendRedirect(req.getContextPath() + "/login");
        } else {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
