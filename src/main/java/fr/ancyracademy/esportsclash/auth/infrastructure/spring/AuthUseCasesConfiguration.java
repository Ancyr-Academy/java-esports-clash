package fr.ancyracademy.esportsclash.auth.infrastructure.spring;

import fr.ancyracademy.esportsclash.auth.application.ports.UserRepository;
import fr.ancyracademy.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import fr.ancyracademy.esportsclash.auth.application.usecases.RegisterCommandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthUseCasesConfiguration {
  @Bean
  public RegisterCommandHandler registerCommandHandler(
      UserRepository userRepository,
      PasswordHasher passwordHasher
  ) {
    return new RegisterCommandHandler(
        userRepository,
        passwordHasher
    );
  }
}
