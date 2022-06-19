package com.amigos.spring.blog.security;

import com.amigos.spring.blog.models.CustomerUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {

    private CustomerUser customerUser;

    public MyUserDetails() {
    }

    public MyUserDetails(CustomerUser customerUser) {
      this.customerUser = customerUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();
        this.customerUser.getRoles().forEach((role) -> {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getRoleName());
            simpleGrantedAuthorityList.add(simpleGrantedAuthority);
        });
        return simpleGrantedAuthorityList;
    }

    @Override
    public String getPassword() {
        return customerUser.getPassword();
    }

    @Override
    public String getUsername() {
        return customerUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
