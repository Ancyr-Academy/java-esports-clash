package fr.ancyracademy.esportsclash.player.infrastructure.spring;

import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;
import fr.ancyracademy.esportsclash.player.application.usecases.CreatePlayerCommandHandler;
import fr.ancyracademy.esportsclash.player.application.usecases.DeletePlayerCommandHandler;
import fr.ancyracademy.esportsclash.player.application.usecases.RenamePlayerCommandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayerUseCaseConfiguration {
  @Bean
  public CreatePlayerCommandHandler createPlayerUseCase(PlayerRepository repository) {
    return new CreatePlayerCommandHandler(repository);
  }

  @Bean
  public RenamePlayerCommandHandler renamePlayerUseCase(PlayerRepository repository) {
    return new RenamePlayerCommandHandler(repository);
  }

  @Bean
  public DeletePlayerCommandHandler deletePlayerUseCase(PlayerRepository repository) {
    return new DeletePlayerCommandHandler(repository);
  }
}
