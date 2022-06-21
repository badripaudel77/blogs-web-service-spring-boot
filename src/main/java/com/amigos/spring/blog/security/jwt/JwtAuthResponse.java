package com.amigos.spring.blog.security.jwt;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class JwtAuthResponse {

    private String authToken;
    private Date generatedTime;
}
