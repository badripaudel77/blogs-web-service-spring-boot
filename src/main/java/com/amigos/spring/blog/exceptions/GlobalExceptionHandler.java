package com.amigos.spring.blog.exceptions;

/*
 - Exception handler for whole application
 */

import com.amigos.spring.blog.utils.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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

    /*
     - Handles all data validation fail message like password, name based on annotation applied using hiberate validator
     - By default spring gives MethodArgumentNotValidException if fields are not valid.
    */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> beanValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> validationErrors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((objectError -> {
            String fieldName = ((FieldError)objectError).getField();
            String message = objectError.getDefaultMessage();
            validationErrors.put(fieldName, message);
        }));

        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    /*
     - Handles all UnauthorizedException
     - If user is not allowed to do that operation.
    */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> unauthorizedException(UnauthorizedException exception) {
        String exceptionMessage = exception.getMessage();
        ExceptionResponse exceptionResponse = new ExceptionResponse(exceptionMessage, exception.getErrorCode());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
