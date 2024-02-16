package fr.ancyracademy.esportsclash.auth.infrastructure.spring;

import fr.ancyracademy.esportsclash.auth.application.services.jwtservice.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, JwtService jwtService) throws Exception {
    http
        .addFilterBefore(
            new JwtAuthenticationFilter(jwtService),
            UsernamePasswordAuthenticationFilter.class
        )
        .authorizeHttpRequests(it ->
            it
                .requestMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
        )
        .formLogin(it -> it.disable())
        .httpBasic(it -> it.disable())
        .csrf(it -> it.disable())
        .sessionManagement(it -> it.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    return http.build();
  }
}
