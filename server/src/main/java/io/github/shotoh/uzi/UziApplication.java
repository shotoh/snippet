package io.github.shotoh.uzi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class UziApplication {
	@Value("${CORS_ALLOWED_ORIGIN:}")
	private String corsAllowedOrigin;

	public static void main(String[] args) {
		SpringApplication.run(UziApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins(corsAllowedOrigin);
			}
		};
	}

	// todo list
	// friend request and remove service/controller
	// patch likes, add num likes to post
}
