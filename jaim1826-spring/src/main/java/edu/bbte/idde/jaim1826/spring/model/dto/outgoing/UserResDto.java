package edu.bbte.idde.jaim1826.spring.model.dto.outgoing;

import lombok.Data;

import java.sql.Date;

@Data
public class UserResDto {
    private Long id;
    private String username;
    private String password;
    private Date lastLogin;
    private Boolean isAdmin;
}
