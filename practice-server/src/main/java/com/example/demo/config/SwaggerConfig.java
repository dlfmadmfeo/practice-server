package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI openAPI() {		
		return new OpenAPI()
				.components(new Components())
				.info(info());
	}
	
	private Info info() {
		return new Info()
				.title("swagger")
				.description("스웨거")
				.version("1.0.0");
	}
}
