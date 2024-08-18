
package com.colegio.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {

	/*
	 * @Bean UserDetailsService userDetailsService() { return new
	 * UsuarioDetailsService(); }
	 */

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*
	 * @Bean DaoAuthenticationProvider authenticationProvider() {
	 * DaoAuthenticationProvider authenticationProvider = new
	 * DaoAuthenticationProvider();
	 * authenticationProvider.setUserDetailsService(userDetailsService());
	 * authenticationProvider.setPasswordEncoder(passwordEndercoder());
	 * 
	 * return authenticationProvider; }
	 * 
	 * @Bean SecurityFilterChain configure(HttpSecurity http) throws Exception {
	 * http.cors(cors -> cors.configure(http)) ;
	 * http.authenticationProvider(authenticationProvider());
	 * http.authorizeHttpRequests(auth -> auth .requestMatchers("/css/**",
	 * "/fonts/**", "/images/**", "/js/**", "/scss/**").permitAll()
	 * .anyRequest().authenticated() ) .httpBasic(withDefaults())
	 * .csrf(AbstractHttpConfigurer::disable) //Propiedad para hacer pruebas (
	 * eneste caso postman) .formLogin( login -> login .loginPage("/login")
	 * .usernameParameter("correo") .passwordParameter("clave")
	 * .defaultSuccessUrl("/") .permitAll() ) .logout( logout ->logout
	 * .logoutSuccessUrl("/loggin?logout") .permitAll() );
	 * 
	 * http .headers(headers -> headers .frameOptions(frameOptions -> frameOptions
	 * .sameOrigin()));
	 * 
	 * 
	 * return http.build();
	 * 
	 * }
	 */
	   // Configuración global de CORS
	 
	  @Override public void addCorsMappings(CorsRegistry registry) {
	  registry.addMapping("/**") .allowedOrigins("https://registro-colegio-angular.vercel.app")
	  .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	 .allowedHeaders("*") .allowCredentials(true); }
	 

}
