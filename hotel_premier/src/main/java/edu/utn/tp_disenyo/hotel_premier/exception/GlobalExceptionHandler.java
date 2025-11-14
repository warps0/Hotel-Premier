package edu.utn.tp_disenyo.hotel_premier.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HuespedNotFoundException.class)
    public ResponseEntity<String> handleHuespedNotFound(HuespedNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HuespedNotSavedException.class)
    public ResponseEntity<String> handleHuespedNotSaved(HuespedNotSavedException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HuespedDuplicatedException.class)
    public ResponseEntity<String> handleHuespedDuplicated(HuespedDuplicatedException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
