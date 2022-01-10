package edu.bbte.idde.jaim1826.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.jaim1826.backend.dao.AbstractDaoFactory;
import edu.bbte.idde.jaim1826.backend.dao.ComponentDao;
import edu.bbte.idde.jaim1826.backend.model.Component;
import edu.bbte.idde.jaim1826.web.utils.ObjectMapperFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/components")
public class CrudComponentServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrudComponentServlet.class);
    private ComponentDao componentDao;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        super.init();
        componentDao = AbstractDaoFactory.getDaoFactory().getComponentDao();
        objectMapper = ObjectMapperFactory.getObjectMapper();
        LOGGER.info("Initialize");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        LOGGER.info("GET request");
        String componentId = req.getParameter("id");
        if (componentId == null) {
            res.setHeader("Content-Type", "application/json");
            objectMapper.writeValue(res.getOutputStream(), componentDao.findAll());
        } else {
            try {
                Long id = Long.parseLong(componentId);
                Component component = componentDao.findById(id);
                if (component == null) {
                    res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    res.getWriter().println("No component found.");
                } else {
                    res.setHeader("Content-Type", "application/json");
                    objectMapper.writeValue(res.getOutputStream(), component);
                }
            } catch (NumberFormatException e) {
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                objectMapper.writeValue(res.getOutputStream(), "Invalid component id.");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        LOGGER.info("POST request");
        try {

            Component component = objectMapper.readValue(req.getInputStream(), Component.class);
            if (component.getCategory() == null || component.getModel() == null) {
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                objectMapper.writeValue(res.getOutputStream(), "Invalid parameter.");
                LOGGER.info("Bad request!");
            } else {
                componentDao.create(component);
                LOGGER.info("Component added successfully.");
                LOGGER.info(component.toString());
                res.setStatus(HttpServletResponse.SC_CREATED);
            }
        } catch (NumberFormatException | IOException e) {
            LOGGER.info(e.toString());
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(res.getOutputStream(), "Invalid parameter.");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
        LOGGER.info("DELETE request");

        try {
            Long componentId = Long.parseLong(req.getParameter("id"));
            Component component = componentDao.findById(componentId);

            if (component == null) {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                componentDao.delete(componentId);
                res.setStatus(HttpServletResponse.SC_NO_CONTENT);
                objectMapper.writeValue(res.getOutputStream(), "Component deleted successfully.");
            }

        } catch (NumberFormatException | IOException e) {
            LOGGER.info(e.toString());
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            objectMapper.writeValue(res.getOutputStream(), "Component with this id was not found.");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) {
        LOGGER.info("PUT request");
        try {
            Long componentId = Long.parseLong(req.getParameter("id"));
            Component oldComponent = componentDao.findById(componentId);

            if (oldComponent == null) {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            Component component = objectMapper.readValue(req.getInputStream(), Component.class);
            setAttributes(oldComponent, component.getCategory(), component.getModel(),
                    component.getPrice(), component.getReleaseYear(), component.getAvailability());

            componentDao.update(oldComponent);
            res.setStatus(HttpServletResponse.SC_OK);
            res.setHeader("Content-Type", "application/json");
            objectMapper.writeValue(res.getOutputStream(), oldComponent);

        } catch (NumberFormatException | IOException
                | ArrayIndexOutOfBoundsException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void setAttributes(Component component, String category, String model,
                              Double price, Integer releaseYear, Boolean availability) {
        if (category != null) {
            component.setCategory(category);
        }
        if (model != null) {
            component.setModel(model);
        }
        if (releaseYear != null) {
            component.setReleaseYear(releaseYear);
        }
        if (price != null) {
            component.setPrice(price);
        }

        if (availability != null) {
            component.setAvailability(availability);
        }

    }

}


