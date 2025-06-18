package com.example.userapi.config;

import com.example.userapi.model.User;
import com.example.userapi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {
    
    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                // Create test user
                User testUser = new User();
                testUser.setUsername("user");
                testUser.setPassword(passwordEncoder.encode("password"));
                testUser.setEmail("user@example.com");
                testUser.setFullName("Test User");
                testUser.setEnabled(true);
                userRepository.save(testUser);
                
                logger.info("Test user created: username=user, password=password");
            }
        };
    }
}
