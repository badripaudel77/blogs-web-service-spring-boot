package com.amigos.spring.blog.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

    private String errorMessage;
    private Integer errorCode;

    public ResourceNotFoundException() {
        super("Requested Resource Not Found");
    }
    public ResourceNotFoundException(String errorMessage, Integer errorCode) {
        super(errorMessage);

        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}
