package com.Idcard.idcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class IdcardApplication {

	@Bean
	public WebMvcConfigurer configurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				System.out.println("Adding CORS HEADERS...");
				registry.addMapping("/**").allowedOrigins("http://localhost:4200") // ✅ Allow only
																					// Angular
																					// frontend
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
						.allowedHeaders("*").allowCredentials(true); // ✅ Important for
																		// authentication
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(IdcardApplication.class, args);
	}

}
