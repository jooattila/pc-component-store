package edu.bbte.idde.jaim1826.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "seller")
public class Seller extends BaseEntity {
    @Column(length = 50)
    private String name;
    @Column(length = 50)
    private String country;
    private Boolean isCompany;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Component> components;
}
