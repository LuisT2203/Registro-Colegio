
package com.colegio.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.colegio.demo.filter.JwtRequestFilter;



import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {

	@Autowired
	private CorsConfig corsConfig;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	private UsuarioDetailsService usuarioDetailsService;
	  
	 

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(usuarioDetailsService); // Aquí se le dice que use tu servicio
		authProvider.setPasswordEncoder(passwordEncoder()); // También se le configura el encoder de contraseñas
		return authProvider;
	}
	
	  
	@Bean 
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
			http.cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource())) 
				.csrf(crf -> crf.disable())
				.authorizeHttpRequests(auth -> auth 
					.requestMatchers("/api/usuario/login").permitAll()
					.anyRequest().authenticated()
					
						) 
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
				.sessionManagement((session) -> session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				);
	
	  
	  return http.build();
	  
	  }
	
	
	
	@Bean
	AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	 
	
	  
	 
		  
		 
}
