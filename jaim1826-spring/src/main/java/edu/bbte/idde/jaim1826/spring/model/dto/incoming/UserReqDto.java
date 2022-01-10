package edu.bbte.idde.jaim1826.spring.model.dto.incoming;


import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class UserReqDto {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
