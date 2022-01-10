package edu.bbte.idde.jaim1826.spring.controller;

import edu.bbte.idde.jaim1826.spring.dao.ComponentDao;
import edu.bbte.idde.jaim1826.spring.exceptions.NotFoundException;
import edu.bbte.idde.jaim1826.spring.model.Component;
import edu.bbte.idde.jaim1826.spring.model.dto.incoming.ComponentReqDto;
import edu.bbte.idde.jaim1826.spring.model.dto.mapper.ComponentMapper;
import edu.bbte.idde.jaim1826.spring.model.dto.outgoing.ComponentResDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/components")
public class ComponentController {
    @Autowired
    private ComponentDao componentDao;

    @Autowired
    private ComponentMapper mapper;

    @GetMapping
    public Collection<ComponentResDto> findAll(
            @RequestParam(name = "category", required = false) String category
//            @RequestParam Optional<Integer> page,
//            @RequestParam Optional<String> sortBy,
//            @RequestParam Optional<Integer> limit
    ) throws SQLException {
        if (category == null) {
            return mapper.componentListToDto(componentDao.findAll());
//                    (PageRequest.of(page.orElse(0), limit.orElse(10), Sort.Direction.ASC,
//                            sortBy.orElse("category"))));
        } else {
            return mapper.componentListToDto(componentDao.findByCategory(category));
        }
    }

    @GetMapping("/{id}")
    public ComponentResDto findById(@PathVariable("id") Long id) throws SQLException {
        Component component = componentDao.getById(id);
        if (component == null) {
            throw new NotFoundException();
        }

        return mapper.componentToComponentDto(component);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid ComponentReqDto component, HttpServletResponse response)
            throws SQLException {
        Component id = componentDao.save(mapper.componentDtoToComponent(component));

        response.addHeader("Location", "/components/" + id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Long id, @RequestBody @Valid ComponentReqDto componentDto)
            throws SQLException {
        Component component = mapper.componentDtoToComponent(componentDto);
        component.setId(id);
        componentDao.save(component);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws SQLException {
        componentDao.deleteById(id);
    }
}
