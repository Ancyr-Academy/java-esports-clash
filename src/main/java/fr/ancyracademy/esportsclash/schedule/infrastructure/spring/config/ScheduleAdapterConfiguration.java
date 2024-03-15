package fr.ancyracademy.esportsclash.schedule.infrastructure.spring.config;

import fr.ancyracademy.esportsclash.schedule.applications.ports.ScheduleDayRepository;
import fr.ancyracademy.esportsclash.schedule.infrastructure.persistence.ram.InMemoryScheduleDayRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScheduleAdapterConfiguration {
  @Bean
  public ScheduleDayRepository scheduleAdapter() {
    return new InMemoryScheduleDayRepository();
  }
}
