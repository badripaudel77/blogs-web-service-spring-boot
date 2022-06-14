package com.amigos.spring.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceAlreadyExistsException extends RuntimeException {

    private String errorMessage;
    private Integer errorCode;

    public ResourceAlreadyExistsException() {
        super("Requested Resource Already Exists");
    }
    public ResourceAlreadyExistsException(String errorMessage, Integer errorCode) {
        super(errorMessage);

        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}
