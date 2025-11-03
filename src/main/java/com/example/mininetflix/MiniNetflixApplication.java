package com.example.mininetflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Mini Netflix application. This class delegates to the
 * Spring Boot framework to bootstrap an embedded web server and configure
 * the application context. Spring scans the package and its subpackages
 * for components such as controllers, services, repositories and entity
 * classes defined elsewhere in this project.
 */
@SpringBootApplication
public class MiniNetflixApplication {

    /**
     * Main method used by the JVM to launch the application.
     *
     * @param args command‑line arguments passed on application startup
     */
    public static void main(String[] args) {
        SpringApplication.run(MiniNetflixApplication.class, args);
    }
}