package com.example.carsharingservice.config;

import com.example.carsharingservice.model.User;
import com.example.carsharingservice.security.jwt.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JwtTokenFilter jwtTokenFilter;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.POST, "/register", "/login")
                            .permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/swagger-ui.html")
                            .permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/cars", "/cars/{id}", "/rentals/{id}",
                                    "/rentals")
                            .authenticated();
                    auth.requestMatchers(HttpMethod.GET, "/users/me")
                            .hasRole(User.Role.CUSTOMER.name());
                    auth.requestMatchers(HttpMethod.POST, "/rentals", "/rentals/{id}/return")
                            .hasRole(User.Role.CUSTOMER.name());
                    auth.requestMatchers(HttpMethod.PUT, "/users/me")
                            .hasRole(User.Role.CUSTOMER.name());
                    auth.requestMatchers(HttpMethod.PUT, "/users/{id}/role", "/cars/{id}")
                            .hasRole(User.Role.MANAGER.name());
                    auth.requestMatchers(HttpMethod.POST, "/cars", "/cars/add/{id}")
                            .hasRole(User.Role.MANAGER.name());
                    auth.requestMatchers(HttpMethod.DELETE, "/cars/{id}", "cars/remove/{id}")
                            .hasRole(User.Role.MANAGER.name());
                })
                .authenticationProvider(authenticationProvider())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
