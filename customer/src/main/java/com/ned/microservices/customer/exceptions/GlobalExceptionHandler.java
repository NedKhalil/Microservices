package com.ned.microservices.customer.exceptions;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomerNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(CustomerNotFoundException e) {
    ErrorResponse customerNotFound = new ErrorResponse(LocalDateTime.now(), e.getMessage(), "Customer Not Found");
    return new ResponseEntity<>(customerNotFound, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> MethodArgumentNotValidException(MethodArgumentNotValidException e) {
    BindingResult result = e.getBindingResult();
    List<FieldError> errors = result.getFieldErrors();
    String message = errors.stream().map(error -> error.getDefaultMessage())
        .reduce("", (acc, err) -> acc + err + ". ");
    ErrorResponse methodArgumentNotValid = new ErrorResponse(LocalDateTime.now(), message,
        "Missing fields in the request");
    return new ResponseEntity<>(methodArgumentNotValid, HttpStatus.BAD_REQUEST);
  }
}
