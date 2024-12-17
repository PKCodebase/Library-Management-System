package com.Library_Management_System.config;

import com.Library_Management_System.exception.CustomAccessDeniedHandler;
import com.Library_Management_System.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtUtil jwtUtil;


    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler; // Inject the custom handler

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/login/signup/**").permitAll()
                        .requestMatchers("/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/books/**","/borrowing/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/books/**").hasRole("ADMIN")  // Only Admin can POST (add books)
                        .requestMatchers(HttpMethod.PUT, "/books/**").hasRole("ADMIN")  // Only Admin can PUT (update books)
                        .requestMatchers(HttpMethod.DELETE, "/books/**").hasRole("ADMIN")  // Only Admin can DELETE books
                        .requestMatchers(HttpMethod.GET,"/borrowings/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/borrowings/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception.accessDeniedHandler(customAccessDeniedHandler)); // Use the custom handler

        return http.build();
    }

}