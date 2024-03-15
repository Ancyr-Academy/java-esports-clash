package fr.ancyracademy.esportsclash.schedule.infrastructure.spring.config;

import fr.ancyracademy.esportsclash.schedule.applications.ports.ScheduleDayRepository;
import fr.ancyracademy.esportsclash.schedule.applications.usecases.CancelMatchCommandHandler;
import fr.ancyracademy.esportsclash.schedule.applications.usecases.OrganizeMatchCommandHandler;
import fr.ancyracademy.esportsclash.team.application.ports.TeamRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScheduleCommandHandlerConfiguration {
  @Bean
  public OrganizeMatchCommandHandler organizeMatchCommandHandler(
      ScheduleDayRepository scheduleDayRepository,
      TeamRepository teamRepository
  ) {
    return new OrganizeMatchCommandHandler(
        scheduleDayRepository,
        teamRepository
    );
  }

  @Bean
  public CancelMatchCommandHandler cancelMatchCommandHandler(
      ScheduleDayRepository scheduleDayRepository
  ) {
    return new CancelMatchCommandHandler(
        scheduleDayRepository
    );
  }
}
