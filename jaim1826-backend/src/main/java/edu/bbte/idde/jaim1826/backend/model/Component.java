package edu.bbte.idde.jaim1826.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Component extends BaseEntity {
    private String category;
    private String model;
    private Integer releaseYear;
    private Double price;
    private Boolean availability;
    private Long sellerId;
}
