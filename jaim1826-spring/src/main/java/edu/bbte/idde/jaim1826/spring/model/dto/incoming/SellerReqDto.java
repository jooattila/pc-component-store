package edu.bbte.idde.jaim1826.spring.model.dto.incoming;

import edu.bbte.idde.jaim1826.spring.model.Component;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Collection;

@Data
public class SellerReqDto {
    @Length(max = 50)
    private String name;
    @Length(max = 50)
    private String country;
    private Boolean isCompany;
    private Collection<Component> components;
}
