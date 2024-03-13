package fr.ancyracademy.esportsclash.auth.infrastructure.spring;

import fr.ancyracademy.esportsclash.auth.application.ports.AuthContext;
import fr.ancyracademy.esportsclash.auth.application.ports.UserRepository;
import fr.ancyracademy.esportsclash.auth.infrastructure.persistence.jpa.SQLUserAccessor;
import fr.ancyracademy.esportsclash.auth.infrastructure.persistence.jpa.SQLUserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthAdaptersConfiguration {
  @Bean
  public UserRepository getUserRepository(EntityManager entityManager, SQLUserAccessor userAccessor) {
    return new SQLUserRepository(
        entityManager,
        userAccessor
    );
  }

  @Bean
  public AuthContext getAuthContext() {
    return new SpringAuthContext();
  }
}
