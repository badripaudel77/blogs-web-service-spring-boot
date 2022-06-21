package com.amigos.spring.blog.apis;

import com.amigos.spring.blog.dtos.CustomerUserDTO;
import com.amigos.spring.blog.exceptions.UnauthorizedException;
import com.amigos.spring.blog.models.CustomerUser;
import com.amigos.spring.blog.security.jwt.JwtAuthRequest;
import com.amigos.spring.blog.security.jwt.JwtAuthResponse;
import com.amigos.spring.blog.security.jwt.JwtTokenHelper;
import com.amigos.spring.blog.services.interfaces.CustomerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerUserService customerUserService;

    @PostMapping("/register")
    public ResponseEntity<CustomerUserDTO> registerCustomerUser(@Valid @RequestBody CustomerUser customerUser) {
        CustomerUserDTO customerUserDTO = customerUserService.registerCustomerUser(customerUser);
        return new ResponseEntity<>(customerUserDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> loginCustomerUser(@RequestBody JwtAuthRequest jwtAuthRequest) {
        // Authenticate username and password
        this.authenticate(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword());

        // Authenticated, so generate token
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());
        String generateToken = jwtTokenHelper.generateToken(userDetails);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAuthToken(generateToken);
        jwtAuthResponse.setGeneratedTime(new Date());

        return new ResponseEntity(jwtAuthResponse, HttpStatus.OK);
    }

    private void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }
        catch (BadCredentialsException exception) {
            throw new UnauthorizedException("Username / Password didn't match", 400); // Make another dedicated exception handler
        }
    }
}
