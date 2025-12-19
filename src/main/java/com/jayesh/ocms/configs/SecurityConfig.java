package com.jayesh.ocms.configs;

import com.jayesh.ocms.filters.JwtAuthFilter;
import com.jayesh.ocms.filters.LoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final JwtAuthFilter jwtAuthFilter;
    private final LoggingFilter loggingFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, LoggingFilter loggingFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.loggingFilter = loggingFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/*").authenticated()
                        .requestMatchers("/user").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("ADMIN")
                        .requestMatchers("/", "/content/*").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(loggingFilter, JwtAuthFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}
