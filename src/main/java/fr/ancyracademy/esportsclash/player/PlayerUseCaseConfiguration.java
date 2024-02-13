package fr.ancyracademy.esportsclash.player;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayerUseCaseConfiguration {
  @Bean
  public CreatePlayerUseCase createPlayerUseCase(PlayerRepository repository) {
    return new CreatePlayerUseCase(repository);
  }
}
