package com.nikola.spring.controler;


import com.nikola.spring.exceptions.DataNotValidatedException;
import com.nikola.spring.exceptions.DuplicateNotAllowed;
import com.nikola.spring.exceptions.FileUploadException;
import com.nikola.spring.exceptions.InstanceUndefinedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

@RestControllerAdvice
public class ErrorControler {

    @ExceptionHandler(DataNotValidatedException.class)
    public ResponseEntity<String> handleDataNotValidatedException(Error error){
        return new ResponseEntity<>(error.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(InstanceUndefinedException.class)
    public ResponseEntity<String> handleInstanceUndefinedException(Error error){

        return new ResponseEntity<>(error.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(DuplicateNotAllowed.class)
    public ResponseEntity<String> handleDuplicateNotAllowed(Error error){
        return new ResponseEntity<>(error.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<String> handleFileUploadException(Error error){
        return new ResponseEntity<>(error.getMessage(),HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<String> handleMultipartException(Exception e){
        String message = e.getMessage();
        return new ResponseEntity<>(message,HttpStatus.EXPECTATION_FAILED);
    }



}
