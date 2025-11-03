package com.example.mininetflix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configures Spring Security for the application. Defines which endpoints
 * require authentication and provides beans for password encoding and
 * authentication management.
 */
@Configuration
public class SecurityConfig {

    /**
     * Define the security filter chain. All public endpoints like the home
     * page, search and authentication endpoints are permitted to everyone.
     * The favourites endpoints require an authenticated user. CSRF is disabled
     * for simplicity in this small demo application.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/search", "/movie/**", "/register", "/login",
                        "/css/**", "/img/**", "/js/**").permitAll()
                .requestMatchers("/favorites/**").authenticated()
                .anyRequest().permitAll())
            .formLogin(form -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/", true)
                    .permitAll())
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .permitAll())
            .csrf(csrf -> csrf.disable());
        return http.build();
    }

    /**
     * Password encoder bean using BCrypt with a default strength. It is used by
     * the {@link com.example.mininetflix.service.UserService} to hash new
     * passwords and by Spring Security to verify credentials.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Expose the {@link AuthenticationManager} to be injected where needed. The
     * underlying manager is obtained from Spring's {@link AuthenticationConfiguration}.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}