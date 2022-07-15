package com.amigos.spring.blog.repositories;

import com.amigos.spring.blog.models.CustomerUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
        assertThat(customerUser.getEmail()).isEqualTo(username); // passes because the username exists in db.
    }
    @Test
    @DisplayName("This should test if user with this username doesn't exist.")
    void testIfUserWithUsernameDoesNotExist() {
        // GIVEN
        String username = "badri-not-found@gmail.com";

        // WHEN
        CustomerUser customerUser = customerUserRepository.findByUsername(username);

        // THEN
        assertThat(customerUser).isNull(); // passes because the username exists in db.
    }


    @AfterEach
    void tearDown() {
        // delete all the test data
        // better to test with in memory db like h2 database
        System.out.println("Do clean up stuff here.");
       // customerUserRepository.deleteAll();
    }

}