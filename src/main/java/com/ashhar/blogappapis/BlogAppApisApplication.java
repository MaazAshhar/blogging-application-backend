package com.ashhar.blogappapis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class BlogAppApisApplication {
	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().load();
        
        SpringApplication app = new SpringApplication(BlogAppApisApplication.class);
        
        // Add properties from .env to Spring's Environment
        Map<String,Object> env = new HashMap<>();
        env.put("DB_URL", dotenv.get("DATABASE_URL"));
        env.put("DB_USERNAME", dotenv.get("PGUSER"));
        env.put("DB_PASSWORD", dotenv.get("PGPASSWORD"));
        env.put("JWT_SECRET_KEY", dotenv.get("JWT_SECRET_KEY"));
        app.setDefaultProperties(env);
        
        app.run(args);
//		SpringApplication.run(BlogAppApisApplication.class, args);
	}
}
