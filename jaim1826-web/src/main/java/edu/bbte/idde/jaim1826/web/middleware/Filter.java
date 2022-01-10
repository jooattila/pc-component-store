package edu.bbte.idde.jaim1826.web.middleware;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebFilter("/component")
public class Filter extends HttpFilter {
    static final Logger LOGGER = LoggerFactory.getLogger(Filter.class);

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        if (req.getSession().getAttribute("username") == null) {
            LOGGER.info("Redirecting to login page");
            LOGGER.info(req.getContextPath());
            res.sendRedirect(req.getContextPath() + "/login.html");
        } else {
            LOGGER.info("Logged in!");
            chain.doFilter(req, res);
        }
    }
}