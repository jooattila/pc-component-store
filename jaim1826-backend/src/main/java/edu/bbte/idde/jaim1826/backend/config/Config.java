package edu.bbte.idde.jaim1826.backend.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Config {
    private String daoType;
    private String databaseUsername;
    private String databasePassword;
    private String databaseURL;
    private String databaseDriver;
    private String connectionPoolSize;
}
