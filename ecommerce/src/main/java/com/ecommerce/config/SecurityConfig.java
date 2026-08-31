package com.ecommerce.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;



import org.springframework.security.web.SecurityFilterChain;
import com.ecommerce.service.CustomUserDetailsService;

import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	    http
	        .csrf(csrf -> csrf.disable())

	        .authorizeHttpRequests(auth -> auth

	            // Public Pages
	            .requestMatchers(
	                    "/",
	                    "/search",
	                    "/login",
	                    "/register",
	                    "/css/**",
	                    "/js/**",
	                    "/images/**")
	            .permitAll()

	            // User Pages
	            .requestMatchers(
	                    "/cart",
	                    "/cart/**",
	                    "/orders",
	                    "/checkout")
	            .hasRole("USER")

	            // Admin Pages
	            .requestMatchers(
	                    "/admin",
	                    "/admin/**",
	                    "/admin/orders",
	                    "/add-product",
	                    "/manage-products",
	                    "/edit-product/**",
	                    "/delete-product/**",
	                    "/save-product",
	                    "/update-product")
	            .hasRole("ADMIN")

	            .anyRequest().authenticated()
	        )

	        .exceptionHandling(exception ->
	            exception.accessDeniedPage("/access-denied")
	        )

	        .formLogin(form -> form
	                .loginPage("/login")
	                .defaultSuccessUrl("/", true)
	                .permitAll()
	        )

	        .logout(logout -> logout
	                .logoutSuccessUrl("/")
	                .permitAll()
	        );

	    return http.build();
	}
	

	@Bean
	public org.springframework.security.crypto.password.PasswordEncoder passwordEncoder() {
		return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {

		DaoAuthenticationProvider auth = new DaoAuthenticationProvider(customUserDetailsService);

	

		auth.setPasswordEncoder(passwordEncoder);

		return auth;
	}

}