package com.amigos.spring.blog.repositories;

import com.amigos.spring.blog.models.CustomerUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Repeat;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// REF : https://stackoverflow.com/a/41316559/9898251
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerUserRepositoryTest {

    @Autowired
    CustomerUserRepository customerUserRepository;

    @Test
    @DisplayName("This should test if user with this username exists.")
    void testIfUserWithUsernameExists() {
        // GIVEN
        String username = "badri@gmail.com";

        // WHEN
        CustomerUser customerUser = customerUserRepository.findByUsername(username);

        // THEN
        // assertThat(customerUser.getEmail()).isNotNull().isEqualTo(username + "."); // fails
        assertThat(customerUser.getEmail()).isEqualTo(username); // passes because the username exists in db.
    }

}