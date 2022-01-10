package edu.bbte.idde.jaim1826.spring.model.dto.incoming;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
public class ComponentReqDto {
    @NotEmpty
    @Length(max = 50)
    private String category;

    @NotEmpty
    @Length(max = 50)
    private String model;

    @Positive
    private Integer releaseYear;

    @Positive
    @NotNull
    private Double price;

    @NotNull
    private Boolean availability;

    @PositiveOrZero
    @NotNull
    private Long sellerId;
}
