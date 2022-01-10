package edu.bbte.idde.jaim1826.spring.model.dto.outgoing;

import edu.bbte.idde.jaim1826.spring.model.Component;
import lombok.Data;

import java.util.Collection;

@Data
public class SellerResDto {
    private Long id;
    private String name;
    private String country;
    private Boolean isCompany;
    private Collection<Component> components;
}
