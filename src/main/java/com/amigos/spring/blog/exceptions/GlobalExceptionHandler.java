package com.amigos.spring.blog.exceptions;

/*
 - Exception handler for whole application
 */

import com.amigos.spring.blog.utils.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
 - @RestControllerAdvice will be responsible for global exception handling for the app
 - @ExceptionHandler(ClassName.class) whenever we throw ClassName exception, this method annotated with
   @ExceptionHandler will be called and will return customized exception message.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
    - Handles all resource not found exception
     */
    // add multiple classes after separating by comma (,)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFoundException(ResourceNotFoundException exception) {
        String exceptionMessage = exception.getMessage();
        ExceptionResponse exceptionResponse = new ExceptionResponse(exceptionMessage, exception.getErrorCode());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    /*
    - Handles all resource already exists exception
     */
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> resourceAlreadyExists(ResourceAlreadyExistsException exception) {
        String exceptionMessage = exception.getMessage();
        ExceptionResponse exceptionResponse = new ExceptionResponse(exceptionMessage, exception.getErrorCode());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
