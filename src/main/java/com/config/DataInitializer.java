package com.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.entity.Role;
import com.entity.Resource;
import com.entity.User;
import com.repository.ResourceRepository;
import com.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            UserRepository userRepository,
            ResourceRepository resourceRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            // =====================================================
            // CREATE ADMIN USER
            // =====================================================

            if (userRepository.findByUsername("admin").isEmpty()) {

                User admin = new User();

                admin.setUsername("admin");

                admin.setPassword(
                        passwordEncoder.encode("admin123")
                );

                admin.setRole(Role.ADMIN);

                userRepository.save(admin);

                System.out.println(
                        "Default ADMIN user created: admin / admin123"
                );
            }


            // =====================================================
            // CREATE NORMAL USER
            // =====================================================

            if (userRepository.findByUsername("kalyan").isEmpty()) {

                User user = new User();

                user.setUsername("kalyan");

                user.setPassword(
                        passwordEncoder.encode("kalyan123")
                );

                user.setRole(Role.USER);

                userRepository.save(user);

                System.out.println(
                        "Default USER created: kalyan / kalyan123"
                );
            }


            // =====================================================
            // CREATE SECOND USER
            // =====================================================

            if (userRepository.findByUsername("user1").isEmpty()) {

                User user1 = new User();

                user1.setUsername("user1");

                user1.setPassword(
                        passwordEncoder.encode("user123")
                );

                user1.setRole(Role.USER);

                userRepository.save(user1);

                System.out.println(
                        "Default USER created: user1 / user123"
                );
            }


            // =====================================================
            // RESOURCE 1
            // =====================================================

            createOrUpdateResource(
                    resourceRepository,
                    "Conference Room",
                    "ROOM",
                    "Conference room for meetings",
                    true
            );


            // =====================================================
            // RESOURCE 2
            // =====================================================

            createOrUpdateResource(
                    resourceRepository,
                    "Meeting Room",
                    "ROOM",
                    "Meeting room for team discussions",
                    true
            );


            // =====================================================
            // RESOURCE 3
            // =====================================================

            createOrUpdateResource(
                    resourceRepository,
                    "Laptop",
                    "EQUIPMENT",
                    "Company laptop for employees",
                    true
            );


            // =====================================================
            // RESOURCE 4
            // =====================================================

            createOrUpdateResource(
                    resourceRepository,
                    "Projector",
                    "EQUIPMENT",
                    "Projector for presentations",
                    true
            );


            // =====================================================
            // INITIALIZATION COMPLETE
            // =====================================================

            System.out.println(
                    "=========================================="
            );

            System.out.println(
                    " Data initialization completed successfully"
            );

            System.out.println(
                    "=========================================="
            );
        };
    }


    // =============================================================
    // CREATE OR UPDATE RESOURCE
    // =============================================================

    private void createOrUpdateResource(
            ResourceRepository resourceRepository,
            String name,
            String type,
            String description,
            boolean available) {

        Resource resource;

        // Check whether resource already exists
        if (resourceRepository.findByName(name).isPresent()) {

            resource = resourceRepository
                    .findByName(name)
                    .get();

            // Update existing resource
            resource.setType(type);
            resource.setDescription(description);
            resource.setAvailable(available);

            resourceRepository.save(resource);

            System.out.println(
                    "Resource updated: " + name
            );

        } else {

            // Create new resource
            resource = new Resource();

            resource.setName(name);
            resource.setType(type);
            resource.setDescription(description);
            resource.setAvailable(available);

            resourceRepository.save(resource);

            System.out.println(
                    "Resource created: " + name
            );
        }
    }
}