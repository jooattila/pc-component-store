package edu.bbte.idde.jaim1826.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@Table(name = "component")
public class Component extends BaseEntity {
    @Column(length = 50)
    private String category;
    @Column(length = 50)
    private String model;
    private Integer releaseYear;
    private Double price;
    private Boolean availability;
}
