package com.colegio.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	private InMemoryUserDetailsManager InMemory;
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http//Le decimos que todo necesita autenticacion
			.authorizeHttpRequests((authorize) -> authorize
									.requestMatchers("/css/**").permitAll()
									.requestMatchers("/fonts/**").permitAll()
									.requestMatchers("/images/**").permitAll()
									.requestMatchers("/js/**").permitAll()
									.requestMatchers("/scss/**").permitAll()
									
										.anyRequest().authenticated()
										)
			.httpBasic(withDefaults())//Metodo de autentifacion 
			.formLogin(form -> form//Formulario que remplazara el que esta por defecto
								.loginPage("/loggin")//controlador para el html del formulario
								.loginProcessingUrl("/logginprocess")//mapeo donde spring procesa la autenticacion(no necesita crearse)
								.permitAll()
					)
			.logout((logout) -> logout.logoutSuccessUrl("/loggin?logout").permitAll());
		http
	    .headers(headers -> headers
	        .frameOptions(frameOptions -> frameOptions
	        .sameOrigin()));
		
		return http.build();
	}

	@Bean
	InMemoryUserDetailsManager userDetailsService () {
		
		UserDetails user1 = User.withDefaultPasswordEncoder()
								.username("Porteria")
								.password("SorRosa2019$")
								.roles("administrador","usuario")
								.build();
		UserDetails user2 = User.withDefaultPasswordEncoder()
				.username("Direccion")
				.password("SorRosa2023$")
				.roles("usuario")
				.build();
		InMemory = new InMemoryUserDetailsManager();
		InMemory.createUser(user1);
		InMemory.createUser(user2);
		return InMemory;
	}
	
	
	

		
}
