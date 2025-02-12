package com.ned.microservices.account.exceptions;

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

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

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

  @ExceptionHandler(FeignException.class)
  public ResponseEntity<ErrorResponse> handleFeignException(FeignException e) {
    var message = e.getMessage();
    var details = "FeingClient Error";
    if (e.status() == 404) {
      message = "customerId not registered in the database";
      details = "Customer Not Found";
    }
    ErrorResponse feignException = new ErrorResponse(LocalDateTime.now(), message, details);
    return new ResponseEntity<>(feignException, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
    ErrorResponse illegalArgumentException = new ErrorResponse(LocalDateTime.now(), e.getMessage(), "Illegal Argument");
    return new ResponseEntity<>(illegalArgumentException, HttpStatus.BAD_REQUEST);
  }
}
