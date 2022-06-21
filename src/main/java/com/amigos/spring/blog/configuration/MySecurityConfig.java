package com.amigos.spring.blog.configuration;

import com.amigos.spring.blog.security.MyUserDetailsService;
import com.amigos.spring.blog.security.jwt.JwtAuthenticationEntryPoint;
import com.amigos.spring.blog.security.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
 ** By default spring security gives us form based auth
 * but for API based auth,we have to convert it into http basic auth type
 *
 * REF 1: https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/user-details.html
 * REF 2 : https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html
 * REF 3 : https://www.toptal.com/spring/spring-security-tutorial [jwt & spring security]
 */
@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    public static final String[] PERMITTED_URLS = {
            "/api/v1/auth/**",
            "/"
    };

    // Tell spring security to use db authentication that we have built using MyUserDetailsService
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // spring security will look at MyUserDetailsService for authentication
        auth.userDetailsService(myUserDetailsService)
                .passwordEncoder(bcryptPasswordEncoder());
    }

    // configure to use jwt
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // .cors().and() // enable cors if ..
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers(PERMITTED_URLS).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

                // authentication filter
        http.
                addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
