/*
 * package com.colegio.demo;
 * 
 * import org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.web.cors.CorsConfiguration; import
 * org.springframework.web.cors.CorsConfigurationSource; import
 * org.springframework.web.cors.UrlBasedCorsConfigurationSource;
 * 
 * import java.util.Arrays;
 * 
 * @Configuration public class CorsConfig {
 * 
 * @Bean CorsConfigurationSource corsConfigurationSource() { CorsConfiguration
 * configuration = new CorsConfiguration();
 * configuration.setAllowCredentials(true);
 * configuration.setAllowedOrigins(Arrays.asList(
 * "https://registro-colegio-angular.vercel.app"));
 * configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT",
 * "DELETE")); configuration.setAllowedHeaders(Arrays.asList("*"));
 * UrlBasedCorsConfigurationSource source = new
 * UrlBasedCorsConfigurationSource(); source.registerCorsConfiguration("/**",
 * configuration); return source; } }
 */