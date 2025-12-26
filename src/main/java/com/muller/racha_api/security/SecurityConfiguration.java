package com.muller.racha_api.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.muller.racha_api.handlers.OAuth2SuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

        private static final String[] swaggerPaths = {
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/v3/api-docs.yaml",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/actuator/health",
                        "/actuator/info"
        };

        private static final String[] publicPaths = {
                        "/auth/login",
                        "/auth/register",
                        "/login/oauth2/code/google",
        };

        @Autowired
        OAuth2SuccessHandler successHandler;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                // CROSS-SITE REQUEST FORGERY
                http.csrf(csrf -> csrf.disable());

                // CORS
                http.cors(Customizer.withDefaults());

                // SESSION MANAGEMENT
                http.sessionManagement(session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

                // AUTHORIZE HTTP REQUESTS
                http.authorizeHttpRequests(
                                authorization -> {
                                        authorization
                                                        .requestMatchers(swaggerPaths).permitAll()
                                                        .requestMatchers(publicPaths).permitAll()
                                                        .anyRequest().authenticated();
                                }).oauth2Client(Customizer.withDefaults())
                                .oauth2Login(oauth -> oauth
                                                .successHandler(successHandler));

                return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration config = new CorsConfiguration();

                config.setAllowedOrigins(List.of("http://localhost:8080", "http://127.0.0.1:8080"));
                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                config.setAllowedHeaders(List.of("*"));
                config.setAllowCredentials(true);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", config);

                return source;
        };

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }
}