package com.klef.fsad.sdp.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;

import com.klef.fsad.sdp.security.JwtFilter;
import com.klef.fsad.sdp.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig 
{
    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserService userService;

    // ✅ FIXED PASSWORD ENCODER
    @Bean
    public PasswordEncoder passwordEncoder() 
    {
        return new BCryptPasswordEncoder();
    }

    // ✅ SECURITY FILTER CHAIN
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception 
    {
        http
            .cors(cors -> {})
            .csrf(csrf -> csrf.disable())

            .authenticationProvider(authenticationProvider())

            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/swagger-ui.html",
                    "/auth/**",
                    "/user/signup",
                    "/admin/**",
                    "/certificate/**"
                ).permitAll()

                .anyRequest().authenticated()
            )

            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ✅ FIXED AUTHENTICATION PROVIDER
    @Bean 
    public AuthenticationProvider authenticationProvider() 
    { 
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); 
        provider.setUserDetailsService(userService); 
        provider.setPasswordEncoder(passwordEncoder()); 
        return provider; 
    }

    // 🔐 AUTH MANAGER
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }

    // 🌐 CORS CONFIG
    @Bean
    public CorsConfigurationSource corsConfigurationSource() 
    {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
