package com.example.EWDOpdracht;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableJpaRepositories("repository")
@EntityScan("domain")
public class EWDOpdrachtApplication implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/home");

		registry.addViewController("/admin");
		registry.addViewController("/book");
	}
	
	public static void main(String[] args) {
		SpringApplication.run(EWDOpdrachtApplication.class, args);
	}

}
