package fr.ancyracademy.esportsclash.player.infrastructure.spring;

import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;
import fr.ancyracademy.esportsclash.player.application.usecases.CreatePlayerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayerUseCaseConfiguration {
  @Bean
  public CreatePlayerUseCase createPlayerUseCase(PlayerRepository repository) {
    return new CreatePlayerUseCase(repository);
  }
}
