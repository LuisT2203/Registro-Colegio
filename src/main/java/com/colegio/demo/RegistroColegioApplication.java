package com.colegio.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan(basePackages="com.colegio")
public class RegistroColegioApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistroColegioApplication.class, args);
	}
	
	
	
	  @Bean public WebMvcConfigurer corsConfigurer() { return new
	  WebMvcConfigurer() {
	  
	  @Override public void addCorsMappings(CorsRegistry registry) {
	  registry.addMapping("/**").allowedOriginPatterns(
	  "https://registro-colegio-angular.vercel.app")
	  .allowedMethods("*").allowedHeaders("*"); } }; }
	 
	 

}
