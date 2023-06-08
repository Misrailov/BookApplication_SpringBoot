package com.example.EWDOpdracht;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import service.AuteurService;
import service.AuteurServiceImpl;
import service.BookService;
import service.BookServiceImpl;
import service.FavouriteService;
import service.FavouriteServiceImpl;
import service.LocatieService;
import service.LocatieServiceImpl;
import service.UserService;
import service.UserServiceImpl;

@SpringBootApplication
@EnableJpaRepositories("repository")
@EntityScan("domain")
public class EWDOpdrachtApplication implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/home");

		registry.addRedirectViewController("/admin", "/home/admin");
		registry.addViewController("/book");
		registry.addRedirectViewController("/book/short", "/bookShort");
		registry.addViewController("/search");
	    registry.addViewController("/403").setViewName("403");
	    registry.addViewController("/404").setViewName("404");



	}
	
	public static void main(String[] args) {
		SpringApplication.run(EWDOpdrachtApplication.class, args);
	}
	@Bean
	LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.ENGLISH);
		return slr;
	}
	
	@Bean
	AuteurService auteurService() {
		return new AuteurServiceImpl();
	}
	@Bean
	BookService bookService() {
		return new BookServiceImpl();
	}
	@Bean
	FavouriteService favouriteService() {
		return new FavouriteServiceImpl();
	}
	@Bean 
	UserService userService() {
		return new UserServiceImpl();
	}
	@Bean
	LocatieService locatieService() {
		return new LocatieServiceImpl();
	}
	

}

