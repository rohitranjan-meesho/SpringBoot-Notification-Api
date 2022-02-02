package com.springrest.notification.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //handler for invalid phone
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<?> handleMethod(MethodArgumentNotValidException exception){
//        ErrorDetails errorDetails =new ErrorDetails(new Date(),"Validation Error!",exception.getBindingResult().getFieldError().getDefaultMessage());
//        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
//    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetails errorDetails =new ErrorDetails(new Date(),"Validation Error!",ex.getBindingResult().getFieldError().getDefaultMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        //return this.handleExceptionInternal(ex, (Object)null, headers, status, request);
    }
}
