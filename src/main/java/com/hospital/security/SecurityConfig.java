package com.hospital.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.authorizeHttpRequests(auth -> auth
						// Public endpoints
						.requestMatchers("/api/auth/**").permitAll().requestMatchers("/api/specializations").permitAll()
						.requestMatchers("/api/doctors").permitAll().requestMatchers("/api/admins/register").permitAll()
						.requestMatchers("/api/admins").permitAll() // ✅ Allow registration
						.requestMatchers("/api/admins/**").authenticated()
						// Protected endpoints - require authentication
						.requestMatchers("/api/patients/**").permitAll() // Temporarily opened for frontend access
						.requestMatchers("/api/appointments/**").permitAll() // Temporarily opened for frontend access
						.requestMatchers("/api/prescriptions/**").authenticated().requestMatchers("/api/analytics/**")
						.authenticated().requestMatchers("/api/admins/**").authenticated().anyRequest().permitAll());

		return http.build();
	}
}
