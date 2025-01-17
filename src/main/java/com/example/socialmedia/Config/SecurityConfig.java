package com.example.socialmedia.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.socialmedia.Filters.JWTFilter;

@Configuration
public class SecurityConfig {

    private final JWTFilter jwtFilter;

    public SecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless APIs
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless
                                                                                                              // session
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers("/api/auth/**").permitAll() // Allow all authentication-related endpoints
                        .requestMatchers(HttpMethod.GET, "/api/users/{username}").permitAll() // Allow public access to
                                                                                              // user profiles
                        .requestMatchers(HttpMethod.GET, "/api/posts/user/{username}").permitAll() // Allow public
                                                                                                   // access to user
                                                                                                   // posts

                        // FollowController endpoints (all protected)
                        .requestMatchers(HttpMethod.POST, "/api/follows/follow").authenticated() // Protect follow
                                                                                                 // endpoint
                        .requestMatchers(HttpMethod.POST, "/api/follows/unfollow").authenticated() // Protect unfollow
                                                                                                   // endpoint
                        .requestMatchers(HttpMethod.GET, "/api/follows/followers/{username}").authenticated() // Protect
                                                                                                              // get
                                                                                                              // followers
                                                                                                              // endpoint
                        .requestMatchers(HttpMethod.GET, "/api/follows/following/{username}").authenticated() // Protect
                                                                                                              // get
                                                                                                              // following
                                                                                                              // endpoint

                        // Protected endpoints from other controllers
                        .requestMatchers(HttpMethod.POST, "/api/posts").authenticated() // Require authentication to
                                                                                        // create a post
                        .requestMatchers(HttpMethod.PUT, "/api/posts/{postId}").authenticated() // Require
                                                                                                // authentication to
                                                                                                // update a post
                        .requestMatchers(HttpMethod.DELETE, "/api/posts/{postId}").authenticated() // Require
                                                                                                   // authentication to
                                                                                                   // delete a post

                        // Catch-all rule for any other endpoint
                        .anyRequest().authenticated() // All other endpoints require authentication
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for password hashing
    }
}