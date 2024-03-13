package fr.ancyracademy.esportsclash.team.infrastructure.spring.config;

import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;
import fr.ancyracademy.esportsclash.team.application.ports.TeamQueries;
import fr.ancyracademy.esportsclash.team.application.ports.TeamRepository;
import fr.ancyracademy.esportsclash.team.application.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeamCommandHandlerConfiguration {
  @Bean
  public CreateTeamCommandHandler createTeamCommandHandler(TeamRepository teamRepository) {
    return new CreateTeamCommandHandler(teamRepository);
  }

  @Bean
  public DeleteTeamCommandHandler deleteTeamCommandHandler(TeamRepository teamRepository) {
    return new DeleteTeamCommandHandler(teamRepository);
  }

  @Bean
  public AddPlayerToTeamCommandHandler addPlayerToTeamCommandHandler(
      PlayerRepository playerRepository,
      TeamRepository teamRepository
  ) {
    return new AddPlayerToTeamCommandHandler(playerRepository, teamRepository);
  }

  @Bean
  public RemovePlayerFromTeamCommandHandler removePlayerFromTeamCommandHandler(
      TeamRepository teamRepository
  ) {
    return new RemovePlayerFromTeamCommandHandler(teamRepository);
  }

  @Bean
  public GetTeamByIdCommandHandler getTeamByIdCommandHandler(TeamQueries teamQueries) {
    return new GetTeamByIdCommandHandler(teamQueries);
  }
}
