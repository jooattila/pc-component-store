package edu.bbte.idde.jaim1826.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity implements Serializable {
    protected Long id;
}
