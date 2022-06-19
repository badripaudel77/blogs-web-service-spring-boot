package com.amigos.spring.blog.security;

import com.amigos.spring.blog.exceptions.ResourceNotFoundException;
import com.amigos.spring.blog.models.CustomerUser;
import com.amigos.spring.blog.repositories.CustomerUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerUserRepository customerUserRepository;

    public MyUserDetailsService() {
        // Default constructor
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // or just do by .findByEmail(username) and proceed as we've treated email as username
        CustomerUser customerUser = customerUserRepository.findByUsername(username);
        if(customerUser == null) {
            throw new ResourceNotFoundException("Customer User Not found with email : "+ username, 404);
        }

        return new MyUserDetails(customerUser);
    }
}
