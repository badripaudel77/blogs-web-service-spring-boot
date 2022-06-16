package com.amigos.spring.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnauthorizedException extends RuntimeException {

    private String errorMessage;
    private Integer errorCode;

    public UnauthorizedException() {
        super("Requested Resource Not Found");
    }
    public UnauthorizedException(String errorMessage, Integer errorCode) {
        super(errorMessage);

        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}
