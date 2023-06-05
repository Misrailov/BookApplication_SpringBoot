package com.example.EWDOpdracht;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	DataSource dataSource;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// auth.jdbcAuthentication().withUser("user").password(new
		// BCryptPasswordEncoder().encode("user")).roles("USER").and().withUser("admin").password(new
		// BCryptPasswordEncoder().encode("admin")).roles("ADMIN",
		// "USER").and().dataSource(dataSource).passwordEncoder(new
		// BCryptPasswordEncoder());
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(new BCryptPasswordEncoder());

	}
	

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().and()
				.authorizeHttpRequests(requests -> 
				requests.requestMatchers("/login**").permitAll()
						.requestMatchers("/book/favorite/*").hasRole("USER")
							.requestMatchers("/book/update/**").hasRole("ADMIN")	
							.requestMatchers("/book/new").hasRole("ADMIN")
						.requestMatchers("/book/*").permitAll()
						.requestMatchers("/css/**").permitAll()
						.requestMatchers("/rest/**").permitAll()
						.requestMatchers("/search**").hasRole("USER")
						.requestMatchers("/rest/book/*").permitAll()
						.requestMatchers("/rest/book/delete/*").hasRole("ADMIN")
						.requestMatchers("/rest/book/create").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/rest/book/delete/*").permitAll()
						.requestMatchers("/403**").permitAll()
						.requestMatchers("book/**").permitAll()
						.requestMatchers("/admin").hasRole("ADMIN")
					

						.requestMatchers("/*")
						.access(new WebExpressionAuthorizationManager("hasRole('ROLE_USER')")))
				.formLogin(form -> form.defaultSuccessUrl("/", true).loginPage("/login").usernameParameter("username")
						.passwordParameter("password"))
				.exceptionHandling().accessDeniedPage("/403");

		return http.build();

	}
	
    @Bean
    public ErrorPageRegistrar errorPageRegistrar() {
        return registry -> {
            registry.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/403"));
            registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
        };
    }
}