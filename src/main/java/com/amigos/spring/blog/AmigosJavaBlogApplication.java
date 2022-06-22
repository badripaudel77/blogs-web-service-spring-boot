package com.amigos.spring.blog;

import com.amigos.spring.blog.models.Role;
import com.amigos.spring.blog.repositories.RoleRepository;
import com.amigos.spring.blog.utils.GlobalConstants;
import com.amigos.spring.blog.utils.MyLogger;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EnableCaching
@SpringBootApplication
@RestController
@RequestMapping("/")
public class AmigosJavaBlogApplication implements CommandLineRunner {
    Logger logger = MyLogger.getMyLogger();

    private RoleRepository roleRepository;
    @Autowired
    public AmigosJavaBlogApplication(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public static void main(String[] args) {
        SpringApplication.run(AmigosJavaBlogApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Application : amigos-spring-blog is up & running at specified port[8000]");
        // create default role on app startup
        Role userRole = new Role(GlobalConstants.DEFAULT_USER_ROLE_ID, GlobalConstants.DEFAULT_USER_ROLE_NAME);
        Role adminRole = new Role(GlobalConstants.DEFAULT_AMDMIN_ROLE_ID, GlobalConstants.ADMIN_ROLE_NAME);
        Role superAdminRole = new Role(GlobalConstants.DEFAULT_SUPER_AMDMIN_ROLE_ID, GlobalConstants.SUPER_ADMIN_ROLE_NAME);

        roleRepository.save(userRole);
        roleRepository.save(adminRole);
        roleRepository.save(superAdminRole);
    }

    @GetMapping("")
    public ResponseEntity<Map> aboutApplication() {
        return new ResponseEntity<Map>(Map.of("name", "amigos spring blog application."), HttpStatus.OK);
    }
}
