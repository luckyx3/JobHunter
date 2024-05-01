package com.hellcaster.JobHunter.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityFilterChainConfig {

    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //Disable CORS
        httpSecurity.cors(AbstractHttpConfigurer::disable);

        //Disable CSRF
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        //Http Request Filter
        httpSecurity.authorizeHttpRequests(
                request ->
                        request.requestMatchers("/api/auth/login/**",
                                                        "/api/auth/sign-up/**").permitAll()
                                                        .anyRequest().authenticated()
        );

        //Authentication Entry Point -> Exception Handler
        httpSecurity.exceptionHandling(
                exceptionConfig -> exceptionConfig.authenticationEntryPoint(authenticationEntryPoint)
        );

        //Set session policy = STATELESS
        httpSecurity.sessionManagement(
                sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        //Add JWT Authentication Filter
        httpSecurity.addFilterBefore((Filter) jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
