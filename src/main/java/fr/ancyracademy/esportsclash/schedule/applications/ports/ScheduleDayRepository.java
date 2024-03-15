package fr.ancyracademy.esportsclash.schedule.applications.ports;

import fr.ancyracademy.esportsclash.core.infrastructure.persistence.BaseRepository;
import fr.ancyracademy.esportsclash.schedule.domain.model.ScheduleDay;

import java.time.LocalDate;
import java.util.Optional;

public interface ScheduleDayRepository extends BaseRepository<ScheduleDay> {
  Optional<ScheduleDay> findByDate(LocalDate date);

  Optional<ScheduleDay> findByMatchId(String matchId);
}
