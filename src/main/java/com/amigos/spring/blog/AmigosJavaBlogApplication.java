package com.amigos.spring.blog;

import com.amigos.spring.blog.utils.MyLogger;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AmigosJavaBlogApplication implements CommandLineRunner {
    Logger logger = MyLogger.getMyLogger();

    public static void main(String[] args) {
        SpringApplication.run(AmigosJavaBlogApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Application : amigos-spring-blog is up & running at specified port[8000]");
    }
}
