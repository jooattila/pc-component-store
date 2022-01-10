package edu.bbte.idde.jaim1826.spring.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.stream.Stream;

@Slf4j
@ControllerAdvice
public class ValidationErrorHandler {
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Stream<String> handleConstraintViolation(ConstraintViolationException e) {
        log.error("Constraint violated", e);
        return e.getConstraintViolations().stream()
                .map(constraintViolation -> constraintViolation.getPropertyPath().toString()
                        + " " + constraintViolation.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Stream<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Method Argument Not Valid Exception", e);
        return e.getBindingResult().getFieldErrors().stream().map(fieldError -> fieldError.getField()
                + " " + fieldError.getDefaultMessage());
    }
}
