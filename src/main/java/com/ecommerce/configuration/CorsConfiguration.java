package com.ecommerce.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@CrossOrigin
public class CorsConfiguration {

	private static final String GET = "GET";
	private static final String POST = "POST";
	private static final String DELETE = "DELETE";
	private static final String PUT = "PUT";

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {

			@Value("${allowed.origins}")
		    private String allowedOrigins;
			
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				// TODO Auto-generated method stub
				 registry.addMapping("/**")
                 .allowedOrigins(allowedOrigins) // Use allowedOriginPatterns instead of allowedOrigins
                 .allowedMethods(GET, POST, DELETE, PUT)
                 .allowedHeaders("*")
                 .allowCredentials(true);
			}

		};
	}

}
