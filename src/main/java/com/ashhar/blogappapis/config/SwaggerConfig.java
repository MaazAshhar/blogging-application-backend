package com.ashhar.blogappapis.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {
	
	public static final String AUTHORIZATION_HEADER="Authorization";
	
	
	@Bean
	OpenAPI swaggerApi() {
		Server server=new Server();
		server.setUrl("http://localhost:9090");
		return new OpenAPI().info(new Info()
				.title("Blogging Application Backend")
				.description("Documentation of all apis of blogging application made by"
						+ "<a href=\"https://www.linkedin.com/in/maaz-ashhar-424b21157/\" target=\"_blank\"><b><u>Maaz Ashhar</u></b></a>"))
				.servers(List.of(server));
	}

}
