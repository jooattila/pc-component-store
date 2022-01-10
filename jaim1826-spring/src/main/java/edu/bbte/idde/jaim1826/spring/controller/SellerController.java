package edu.bbte.idde.jaim1826.spring.controller;

import edu.bbte.idde.jaim1826.spring.dao.SellerDao;
import edu.bbte.idde.jaim1826.spring.exceptions.NotFoundException;
import edu.bbte.idde.jaim1826.spring.model.Seller;
import edu.bbte.idde.jaim1826.spring.model.dto.incoming.SellerReqDto;
import edu.bbte.idde.jaim1826.spring.model.dto.mapper.SellerMapper;
import edu.bbte.idde.jaim1826.spring.model.dto.outgoing.SellerResDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.Collection;

@RestController
@RequestMapping("/sellers")
public class SellerController {
    @Autowired
    private SellerDao sellerDao;

    @Autowired
    private SellerMapper mapper;

    @GetMapping
    public Collection<SellerResDto> findAll(@RequestParam(name = "country", required = false) String country)
            throws SQLException {
        if (country == null) {
            return mapper.sellerListToDto(sellerDao.findAll());
        } else {
            return mapper.sellerListToDto(sellerDao.findByCountry(country));
        }
    }

    @GetMapping("/{id}")
    public SellerResDto findById(@PathVariable("id") Long id) throws SQLException {
        Seller seller = sellerDao.getById(id);
        if (seller == null) {
            throw new NotFoundException();
        }

        return mapper.sellerToSellerDto(seller);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid SellerReqDto seller, HttpServletResponse response)
            throws SQLException {
        Seller id = sellerDao.save(mapper.sellerDtoToSeller(seller));

        response.addHeader("Location", "/sellers/" + id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Long id, @RequestBody @Valid SellerReqDto sellerReqDto)
            throws SQLException {
        Seller seller = mapper.sellerDtoToSeller(sellerReqDto);
        seller.setId(id);

        sellerDao.save(seller);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws SQLException {
        sellerDao.deleteById(id);
    }
}

