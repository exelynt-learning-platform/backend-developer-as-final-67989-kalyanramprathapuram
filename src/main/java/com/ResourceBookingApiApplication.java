package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ResourceBookingApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourceBookingApiApplication.class, args);
        System.out.println("App is working...");
    }

}