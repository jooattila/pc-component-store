package edu.bbte.idde.jaim1826.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class Seller extends BaseEntity {

    private String name;
    private String country;
    private Boolean isCompany;
}
