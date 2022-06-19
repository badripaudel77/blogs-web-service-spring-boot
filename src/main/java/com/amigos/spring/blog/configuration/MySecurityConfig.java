package com.amigos.spring.blog.configuration;

import com.amigos.spring.blog.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
 ** By default spring security gives us form based auth
 * but for API based auth,we have to convert it into http basic auth type
 *
 * REF 1: https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/user-details.html
 * REF 2 : https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html
 */
@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    // Tell spring security to use db authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // spring security will look at MyUserDetailsService for authentication
        auth.userDetailsService(myUserDetailsService)
                .passwordEncoder(bcryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic();

    }


    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
