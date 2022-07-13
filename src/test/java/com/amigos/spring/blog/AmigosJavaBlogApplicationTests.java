package com.amigos.spring.blog;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AmigosJavaBlogApplicationTests {

    /*
    * Test - denotes test class
    *  DisplayName("some name") used to give a name to test case
    * Order(orderNumber) specify the order
    * RepeatedTest(count) repeat that no of times
     */
    @Test
    @DisplayName("Test Method to see if it's working")
    @RepeatedTest(2)
    @Order(1)
    void contextLoads() {
        System.out.println("-------------- Welcome to amigos test " +
                "--------------\n");
    }

    @Test
    @DisplayName("execute before every test")
    @BeforeEach
    void executeBeforeEveryTest() {
        System.out.println("Before every " );
    }

    @Test
    @DisplayName("Check if two numbers are equal")
    void testIfTwoNumbersAreEqual() {
      int number = 20;
      int anotherNumber = 20;
      assertThat(number).isEqualTo(anotherNumber);
    }

    /* Test home (/) endpoint
     * Basically test status code,
     * Response [payload (JSON)]
     * headers etcetera
     */
    @Test
    @DisplayName("Test Home End Point at appContext/")
    void testHomeEndPoint() {
        // given
        String url = "/";
        // expected payload
        String expectedOutput = "amigos spring blog application.";

        // assertThat(url).isEqualTo(expectedOutput); // TODO : to be done
    }
}
