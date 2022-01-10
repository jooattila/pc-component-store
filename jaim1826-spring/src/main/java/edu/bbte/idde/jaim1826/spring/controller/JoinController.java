package edu.bbte.idde.jaim1826.spring.controller;

import edu.bbte.idde.jaim1826.spring.dao.ComponentDao;
import edu.bbte.idde.jaim1826.spring.dao.SellerDao;
import edu.bbte.idde.jaim1826.spring.model.Component;
import edu.bbte.idde.jaim1826.spring.model.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Collection;

@RestController
@RequestMapping("/seller/{id}/components")
public class JoinController {

    @Autowired
    ComponentDao componentDao;

    @Autowired
    SellerDao sellerDao;

    @GetMapping
    public Collection<Component> findComponentsForSeller(@PathVariable("id") Long sellerId)
            throws SQLException {
        Seller seller = sellerDao.getById(sellerId);
        return seller.getComponents();
    }

    @PostMapping("/{componentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSellerComponent(@PathVariable("componentId") Long componentId,
                                      @PathVariable("id") Long sellerId, HttpServletResponse response)
            throws SQLException {
        Seller seller = sellerDao.getById(sellerId);
        Component component = componentDao.getById(componentId);
        if (component == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            Collection<Component> components = seller.getComponents();
            components.add(component);
            seller.setComponents(components);
            sellerDao.save(seller);
        }
    }

    @DeleteMapping("/{componentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSellerComponent(@PathVariable("componentId") Long componentId,
                                      @PathVariable("id") Long sellerId, HttpServletResponse response)
            throws SQLException {
        Seller seller = sellerDao.getById(sellerId);
        Component component = componentDao.getById(componentId);
        if (component == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            Collection<Component> components = seller.getComponents();
            components.remove(component);
            seller.setComponents(components);
            sellerDao.save(seller);
        }
    }
}