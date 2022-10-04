package com.example.backend;

import com.example.backend.user.User;
import com.example.backend.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    @Profile("!prod")
    CommandLineRunner createInitialUsers(UserService userService) {
        return args -> {
            for (int i = 1; i <= 100; i++) {
                User user = new User();
                user.setUsername("user" + i);
                user.setDisplayName("display" + i);
                user.setPassword("P4ssword");
                userService.save(user);
            }
        };
    }

}
