package com.amigos.spring.blog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AmigosJavaBlogApplication implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(AmigosJavaBlogApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AmigosJavaBlogApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Application : amigos-spring-blog is up & running at specified port[8000]");
    }
}
