package com.amigos.spring.blog;

import com.amigos.spring.blog.utils.MyLogger;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
@RequestMapping("/")
public class AmigosJavaBlogApplication implements CommandLineRunner {
    Logger logger = MyLogger.getMyLogger();

    public static void main(String[] args) {
        SpringApplication.run(AmigosJavaBlogApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Application : amigos-spring-blog is up & running at specified port[8000]");
    }

    @GetMapping("")
    public ResponseEntity<Map> aboutApplication() {
        return new ResponseEntity<Map>(Map.of("name", "amigos spring blog application."), HttpStatus.OK);
    }
}
