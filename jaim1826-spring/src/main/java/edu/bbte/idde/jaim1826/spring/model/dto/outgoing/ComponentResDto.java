package edu.bbte.idde.jaim1826.spring.model.dto.outgoing;

import lombok.Data;

@Data
public class ComponentResDto {
    private Long id;
    private String category;
    private String model;
    private Integer releaseYear;
    private Double price;
    private Boolean availability;
    private Long sellerId;
}
