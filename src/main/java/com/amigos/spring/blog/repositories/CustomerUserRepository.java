package com.amigos.spring.blog.repositories;

/*
 - contains all the methods related to accessing data from Database.
 */
import com.amigos.spring.blog.models.CustomerUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerUserRepository extends JpaRepository<CustomerUser, Long> {

    Optional<CustomerUser> findByEmail(String email);
}
