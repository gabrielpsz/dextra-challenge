package com.gabrielpsz.dextrachallenge.controller.exceptions;

import com.gabrielpsz.dextrachallenge.exceptions.EmptyRequestContent;
import com.gabrielpsz.dextrachallenge.utils.ApiError;
import com.gabrielpsz.dextrachallenge.utils.Tools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmptyRequestContent.class) // 400 error
    public ResponseEntity<Object> handleEmptyRequestContent(EmptyRequestContent ex) {
        ApiError error = new ApiError(ex.getCode(), ex.getStatus());
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Object> handleNumberFormatException(NumberFormatException ex) {
        log.error("An error occurred processing request " + ex);
        ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), "The ID must be a integer.");
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralError(Exception ex) {
        log.error("An error occurred processing request " + ex);
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException ex) {
        log.error("An error occurred processing request " + ex);
        String responseBodyAsString = ex.getResponseBodyAsString();
        String message = Tools.getStringJsonFieldValue(responseBodyAsString, "status");
        ApiError error = new ApiError(ex.getRawStatusCode(), message != null ? message : ex.getMessage());
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<Object> handleHttpServerErrorException(HttpServerErrorException ex) {
        log.error("An error occurred processing request " + ex);
        String responseBodyAsString = ex.getResponseBodyAsString();
        String message = Tools.getStringJsonFieldValue(responseBodyAsString, "status");
        ApiError error = new ApiError(ex.getRawStatusCode(), message != null ? message : ex.getMessage());
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
