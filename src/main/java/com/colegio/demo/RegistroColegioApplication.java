package com.colegio.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages="com.colegio")
public class RegistroColegioApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistroColegioApplication.class, args);
	}
	
	
	
	 
	 
	 

}
