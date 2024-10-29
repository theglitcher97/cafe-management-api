package com.inn.cafe.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Autowired private CustomerUserDetailService customerUserDetailService;
  @Autowired private JwtFilter jwtFilter;
  @Autowired private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


//  @Bean
//  public void authManager(AuthenticationManagerBuilder auth) throws Exception {
//    auth.userDetailsService(this.customerUserDetailService);
//  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration )
      throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

    // set security for our routes
    httpSecurity.authorizeHttpRequests(
        configure ->
            configure
                .requestMatchers("/auth/login", "/auth/signup", "auth/forgot-password")
                .permitAll()
                .anyRequest()
                .authenticated()
    );


    httpSecurity.exceptionHandling(exception -> exception.authenticationEntryPoint(this.jwtAuthenticationEntryPoint));

    httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    // disable cors
    httpSecurity.cors(cors -> cors.disable());

    //disable csrf
    httpSecurity.csrf(csrf -> csrf.disable());

    // apply filter for each request
    httpSecurity.addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
  }
}
