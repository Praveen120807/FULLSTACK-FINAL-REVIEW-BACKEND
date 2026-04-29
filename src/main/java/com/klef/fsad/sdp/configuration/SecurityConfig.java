//package com.klef.fsad.sdp.configuration;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import com.klef.fsad.sdp.security.JwtFilter;
//import com.klef.fsad.sdp.service.UserService;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig 
//{
//    @Autowired
//    private JwtFilter jwtFilter;
//
//    @Autowired
//    private UserService userService;
//
//    @Bean
//    public org.springframework.security.crypto.password.PasswordEncoder passwordEncoder() 
//    {
//        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
//    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception 
//    {
//        http
//            .cors(cors -> {}) //enable CORS
//            .csrf(csrf -> csrf.disable())
//            .authenticationProvider(authenticationProvider())
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers(
//                    "/swagger-ui/**",
//                    "/v3/api-docs/**",
//                    "/swagger-ui.html",
//                    "/auth/**",
//                    "/user/signup",
//                    "/demoapi/**",
//                    "/admin/**",
//                    "/certificate/**" 
//
//                ).permitAll()
//
//                .requestMatchers("/adminapi/**").hasAuthority("ADMIN")
//                .requestMatchers("/customerapi/**").hasAuthority("CUSTOMER")
//                .requestMatchers("/managerapi/**").hasAuthority("MANAGER")
//
//                .anyRequest().authenticated()
//            )
//            .sessionManagement(session ->
//                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            )
//            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//    @Bean 
//    public AuthenticationProvider authenticationProvider() 
//    { 
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userService); 
//        provider.setPasswordEncoder(passwordEncoder()); 
//        return provider; 
//    }
//
//    
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
//    {
//        return config.getAuthenticationManager();
//    }
//
//   
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() 
//    {
//        CorsConfiguration config = new CorsConfiguration();
//
//        config.setAllowedOrigins(List.of("http://localhost:5174/")); // frontend url
//        
//        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
//        config.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//
//        return source;
//    }
//}
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

    
    @Bean
    public PasswordEncoder passwordEncoder() 
    {
        return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
    }

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

    @Bean 
    public AuthenticationProvider authenticationProvider() 
    { 
      DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userService); 
      provider.setPasswordEncoder(passwordEncoder()); 
      return provider; 
     }

    // 🔐 Authentication Manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }

    // 🌐 CORS Configuration
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