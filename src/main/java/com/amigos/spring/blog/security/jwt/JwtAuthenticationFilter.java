package com.amigos.spring.blog.security.jwt;

import com.amigos.spring.blog.exceptions.ResourceNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// REF : https://www.youtube.com/watch?v=P_29bHsVjjg&list=PL0zysOflRCen-GihOcm1hZfYAlwr63K_M&index=33
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Get the token
        String requestToken = request.getHeader("Authorization");
        // Bearer token_value
        String username = null;
        String token = null;

        // check if there is token & starts with Bearer
        if (requestToken != null && requestToken.startsWith("Bearer")) {
            token = requestToken.substring(7);
            try {
                username = this.jwtTokenHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                throw new ResourceNotFoundException("Unable to get authentication token", 404);
            } catch (ExpiredJwtException e) {
                throw new ResourceNotFoundException("Token has expired.", 404);
            } catch (MalformedJwtException e) {
                throw new ResourceNotFoundException("Invalid token", 404);
            }
        }
        else {
            System.out.println("Token doesn't start with Bearer keyword");
        }

        // once we get the token , now validate
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (this.jwtTokenHelper.validateToken(token, userDetails)) {
                //have to do authentication
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            else {
                System.out.println("Invalid token.");
            }
        }
        else {
            System.out.println("Username is null or context is not null");
        }
        filterChain.doFilter(request, response);
    }
}
