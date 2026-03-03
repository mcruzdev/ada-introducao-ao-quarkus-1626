package com.ada.challenge.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

/**
 * Base test class that configures REST Assured to connect to an external running application
 */
public abstract class BaseTest {
    
    @BeforeAll
    public static void configureRestAssured() {
        // Get configuration from system properties or use defaults
        String baseUrl = System.getProperty("test.base.url", "http://localhost:8080");
        String basePath = System.getProperty("test.base.path", "");
        
        RestAssured.baseURI = baseUrl;
        RestAssured.basePath = basePath;
        
        System.out.println("🔗 Connecting to external application at: " + baseUrl + basePath);
    }
}

// Made with Bob