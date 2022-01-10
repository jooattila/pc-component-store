package edu.bbte.idde.jaim1826.web.servlets;

import com.github.jknack.handlebars.Template;
import edu.bbte.idde.jaim1826.backend.dao.AbstractDaoFactory;
import edu.bbte.idde.jaim1826.backend.dao.ComponentDao;
import edu.bbte.idde.jaim1826.backend.model.Component;
import edu.bbte.idde.jaim1826.web.utils.HbsTemplateFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;

@WebServlet("/component")
public class HbsComponentServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(HbsComponentServlet.class);
    private static final ComponentDao COMPONENT_DAO = AbstractDaoFactory.getDaoFactory().getComponentDao();

    @Override
    public void init() throws ServletException {
        super.init();
        COMPONENT_DAO.create(new Component("Video card", "GeForce RTX 2060", 2019, 3500.0, true, 1L));
        COMPONENT_DAO.create(new Component("Motherboard", "\n"
                + "Gigabyte GA-A320M-S2H", 2017, 500.0, false, 1L));
        COMPONENT_DAO.create(new Component("Memory RAM", "HyperX Fury RGB 16GB", 2020, 900.0, true, 1L));

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Collection<Component> model;
        model = COMPONENT_DAO.findAll();
        Template view = HbsTemplateFactory.getTemplate("component");
        view.apply(model, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().invalidate();
        LOGGER.info("Logged out successfully.");
        resp.sendRedirect(req.getContextPath() + "/login.html");
    }
}



