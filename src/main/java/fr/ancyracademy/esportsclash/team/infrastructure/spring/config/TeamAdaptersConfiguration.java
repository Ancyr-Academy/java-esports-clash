package fr.ancyracademy.esportsclash.team.infrastructure.spring.config;

import fr.ancyracademy.esportsclash.team.application.ports.TeamRepository;
import fr.ancyracademy.esportsclash.team.infrastructure.persistence.ram.InMemoryTeamRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeamAdaptersConfiguration {
  @Bean
  public TeamRepository teamRepository() {
    return new InMemoryTeamRepository();
  }
}
