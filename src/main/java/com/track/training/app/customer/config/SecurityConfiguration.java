package com.track.training.app.customer.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.track.training.app.customer.customer.adapters.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        return http
            .csrf(csrf -> 
                csrf
                .disable())
            .authorizeHttpRequests(authRequest ->
              authRequest
                .requestMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
                )
            .sessionManagement(sessionManager->
                sessionManager 
                  .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
            
            
    }

    @Bean
    AuditorAware<String> springSecurityAuditorAware() {
        return new AuditorAware<String>() {

            @Override
            public Optional<String> getCurrentAuditor() {
                return Optional.of(getCurrentUserLogin().orElse("system"));
            }

            /**
             * Get the login of the current user.
             *
             * @return the login of the current user.
             */
            public static Optional<String> getCurrentUserLogin() {
                SecurityContext securityContext = SecurityContextHolder.getContext();
                return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
            }

            private static String extractPrincipal(Authentication authentication) {
                if (authentication == null) {
                    return null;
                } else if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
                    return springSecurityUser.getUsername();
                } else if (authentication.getPrincipal() instanceof String stringPrincipal) {
                    return stringPrincipal;
                }
                return null;
            }

        };
    }
}
