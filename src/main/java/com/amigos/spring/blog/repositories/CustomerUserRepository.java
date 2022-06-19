package com.amigos.spring.blog.repositories;

/*
 - contains all the methods related to accessing data from Database.
 */
import com.amigos.spring.blog.models.CustomerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface CustomerUserRepository extends JpaRepository<CustomerUser, Long> {

    Optional<CustomerUser> findByEmail(String email);

    @Query("select u from CustomerUser u where u.email= :email")
    CustomerUser findByUsername(@Param("email") String email);
}
