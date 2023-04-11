package com.example.spring_la_mia_pizzeria_crud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    //    .requestMatchers("/categories", "/categories/**").hasAuthority("ADMIN")
    //        .requestMatchers("/borrowings/**").hasAuthority("ADMIN")
    //        .requestMatchers("/books/create", "/books/edit/**", "/books/delete/**")
    //        .hasAuthority("ADMIN")
    //        .requestMatchers("/", "/books", "/books/**").hasAnyAuthority("USER", "ADMIN")
    //        .requestMatchers(HttpMethod.POST, "/books/**").hasAuthority("ADMIN")
    //        .requestMatchers("/**").permitAll()
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/pizzas").permitAll()
                .requestMatchers("/webjars/**").permitAll()//autorizzo tutta la cartella
                .requestMatchers("/pizzas/create", "/pizzas/edit").hasAuthority("ADMIN")
                .requestMatchers("/api/**").permitAll()
                .requestMatchers("/").permitAll()
                .and().formLogin()
                .and().logout()
                .and().exceptionHandling();
        http.csrf().disable();
        return http.build();
    }

    @Bean
    DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
