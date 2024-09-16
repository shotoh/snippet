package io.github.shotoh.snippet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SnippetApplication {
	@Value("${CORS_ALLOWED_ORIGIN:}")
	private String corsAllowedOrigin;

	public static void main(String[] args) {
		SpringApplication.run(SnippetApplication.class, args);
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
	// add one-to-many or many-to-many relations for comments, likes, and media in posts
	// fix media, validate file path and store when creating?
	// friend status change from enum?
}
