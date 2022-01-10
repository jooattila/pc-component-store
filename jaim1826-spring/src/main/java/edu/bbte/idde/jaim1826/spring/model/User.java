package edu.bbte.idde.jaim1826.spring.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "user")
public class User extends BaseEntity {
    private String username;
    private String password;
    private Boolean isAdmin;
    private Date lastLogin;
}
