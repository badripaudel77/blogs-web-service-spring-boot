package com.amigos.spring.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AmigosJavaBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(AmigosJavaBlogApplication.class, args);
        System.out.println("----------------\nApplication : amigos-spring-blog is up & running at specified port[8080]----------------\n");
    }
}
