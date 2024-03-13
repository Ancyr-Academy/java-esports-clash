package fr.ancyracademy.esportsclash.team.infrastructure.spring.config;

import fr.ancyracademy.esportsclash.team.application.ports.TeamRepository;
import fr.ancyracademy.esportsclash.team.application.usecases.CreateTeamCommandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeamCommandHandlerConfiguration {
  @Bean
  public CreateTeamCommandHandler createTeamCommandHandler(TeamRepository teamRepository) {
    return new CreateTeamCommandHandler(teamRepository);
  }
}
