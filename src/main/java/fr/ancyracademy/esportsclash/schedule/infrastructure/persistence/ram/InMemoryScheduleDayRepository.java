package fr.ancyracademy.esportsclash.schedule.infrastructure.persistence.ram;

import fr.ancyracademy.esportsclash.core.infrastructure.persistence.ram.InMemoryBaseRepository;
import fr.ancyracademy.esportsclash.schedule.applications.ports.ScheduleDayRepository;
import fr.ancyracademy.esportsclash.schedule.domain.model.ScheduleDay;

import java.time.LocalDate;
import java.util.Optional;

public class InMemoryScheduleDayRepository
    extends InMemoryBaseRepository<ScheduleDay>
    implements ScheduleDayRepository {
  @Override
  public Optional<ScheduleDay> findByDate(LocalDate date) {
    return entities.values().stream()
        .filter(scheduleDay -> scheduleDay.getDay().equals(date))
        .findFirst();
  }

  @Override
  public Optional<ScheduleDay> findByMatchId(String matchId) {
    return entities.values().stream()
        .filter(scheduleDay -> scheduleDay.containsMatch(matchId))
        .findFirst();
  }
}
