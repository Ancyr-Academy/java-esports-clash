package fr.ancyracademy.esportsclash.player;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(it -> it.anyRequest().permitAll())
        .formLogin(it -> it.disable())
        .httpBasic(it -> it.disable())
        .csrf(it -> it.disable())
        .sessionManagement(it -> it.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    
    return http.build();
  }
}
