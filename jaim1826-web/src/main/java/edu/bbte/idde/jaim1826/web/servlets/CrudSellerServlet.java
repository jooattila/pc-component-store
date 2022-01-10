package edu.bbte.idde.jaim1826.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.jaim1826.backend.dao.AbstractDaoFactory;
import edu.bbte.idde.jaim1826.backend.dao.SellerDao;
import edu.bbte.idde.jaim1826.backend.model.Seller;
import edu.bbte.idde.jaim1826.web.utils.ObjectMapperFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/sellers")
public class CrudSellerServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrudComponentServlet.class);
    private SellerDao sellerDao;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        super.init();
        sellerDao = AbstractDaoFactory.getDaoFactory().getSellerDao();
        objectMapper = ObjectMapperFactory.getObjectMapper();
        LOGGER.info("Initialize");
        sellerDao.create(new Seller("Jancsi", "Romania", false));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        LOGGER.info("GET request");
        String sellerId = req.getParameter("id");
        if (sellerId == null) {
            res.setHeader("Content-Type", "application/json");
            objectMapper.writeValue(res.getOutputStream(), sellerDao.findAll());
        } else {
            try {
                Long id = Long.parseLong(sellerId);
                Seller seller = sellerDao.findById(id);
                if (seller == null) {
                    res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    res.getWriter().println("No seller found.");
                } else {
                    res.setHeader("Content-Type", "application/json");
                    objectMapper.writeValue(res.getOutputStream(), seller);
                }
            } catch (NumberFormatException e) {
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                objectMapper.writeValue(res.getOutputStream(), "Invalid seller id.");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        LOGGER.info("POST request");

        try {

            Seller seller = objectMapper.readValue(req.getInputStream(), Seller.class);
            if (seller.getCountry() == null || seller.getIsCompany() == null || seller.getName() == null) {
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                objectMapper.writeValue(res.getOutputStream(), "Invalid parameter.");
                LOGGER.info("Bad request!");
            } else {
                sellerDao.create(seller);
                LOGGER.info("Seller added successfully.");
                LOGGER.info(seller.toString());
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
            Long sellerId = Long.parseLong(req.getParameter("id"));
            Seller seller = sellerDao.findById(sellerId);

            if (seller == null) {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                sellerDao.delete(sellerId);
                res.setStatus(HttpServletResponse.SC_NO_CONTENT);
                objectMapper.writeValue(res.getOutputStream(), "Seller deleted successfully.");
            }
        } catch (NumberFormatException | IOException e) {
            LOGGER.info(e.toString());
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            objectMapper.writeValue(res.getOutputStream(), "Seller with this id was not found.");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) {
        LOGGER.info("PUT request");
        try {

            Long sellerId = Long.parseLong(req.getParameter("id"));
            Seller oldSeller = sellerDao.findById(sellerId);
            if (oldSeller == null) {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            Seller seller = objectMapper.readValue(req.getInputStream(), Seller.class);
            setAttributes(oldSeller, seller.getName(), seller.getCountry(), seller.getIsCompany());
            sellerDao.update(oldSeller);
            res.setStatus(HttpServletResponse.SC_OK);
            res.setHeader("Content-Type", "application/json");
            objectMapper.writeValue(res.getOutputStream(), oldSeller);

        } catch (NumberFormatException | IOException
                | ArrayIndexOutOfBoundsException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void setAttributes(Seller seller, String name, String country, Boolean isCompany) {
        if (name != null) {
            seller.setName(name);
        }
        if (country != null) {
            seller.setCountry(country);
        }
        if (isCompany != null) {
            seller.setIsCompany(isCompany);
        }
    }

}
