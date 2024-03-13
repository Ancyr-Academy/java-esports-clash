package fr.ancyracademy.esportsclash.auth.infrastructure.spring;

import fr.ancyracademy.esportsclash.auth.application.services.jwtservice.ConcreteJwtService;
import fr.ancyracademy.esportsclash.auth.application.services.jwtservice.JwtService;
import fr.ancyracademy.esportsclash.auth.application.services.passwordhasher.BcryptPasswordHasher;
import fr.ancyracademy.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthServicesConfiguration {
  @Bean
  public PasswordHasher passwordHasher() {
    return new BcryptPasswordHasher();
  }

  @Bean
  public JwtService jwtService() {
    return new ConcreteJwtService(
        "supersekretpleasechangeitwithsomethingbetter",
        3600
    );
  }
}
