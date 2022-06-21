package com.amigos.spring.blog.security.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 ** 8 steps to implement JWT in spring security [steps from Learn code with durgesh youtube channel]
  1. Add dependency(io.jsonwebtoken)
  2. Create JWT authenticationEntryPoint implements AuthenticationEntryPoint
  3. Create JWTTokenHelper
  4. JwtAuthenticationFilter  extends OnceRequestFilter
     - Get jwt token from request
     - Validate token
     - Get user from token
     - Load user associated with token
     - Set spring security
  5. Create JwtAuthResponse
  6. Configure JWT in spring security config
  7. Create login  api to return token
  8. Test the application.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed.");
    }
}
