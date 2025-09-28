//package com.course.onlinecoursemanagement.auth;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableMethodSecurity
//public class SecurityConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
/// /    @Bean
/// /    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtAuthenticationFilter jwtFilter) throws Exception {
/// /        httpSecurity.
/// /                csrf(AbstractHttpConfigurer::disable)
/// /                .sessionManagement(sees -> sees.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
/// /                .authorizeHttpRequests(auth -> {
/// /                    auth.requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
/// /                            .requestMatchers("/admin/**").hasRole("ADMIN")
/// /                            .anyRequest().authenticated();
/// /                })
/// /                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
/// /        return httpSecurity.build();
/// /    }
//}
