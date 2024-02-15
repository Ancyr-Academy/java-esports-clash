package fr.ancyracademy.esportsclash.auth.infrastructure.spring;

import fr.ancyracademy.esportsclash.auth.application.infrastructure.persistence.ram.InMemoryUserRepository;
import fr.ancyracademy.esportsclash.auth.application.ports.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthAdaptersConfiguration {
  @Bean
  public UserRepository getUserRepository() {
    return new InMemoryUserRepository();
  }
}
