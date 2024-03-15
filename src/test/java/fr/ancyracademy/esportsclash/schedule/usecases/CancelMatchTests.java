package fr.ancyracademy.esportsclash.schedule.usecases;

import fr.ancyracademy.esportsclash.core.domain.exceptions.NotFoundException;
import fr.ancyracademy.esportsclash.schedule.applications.usecases.CancelMatchCommand;
import fr.ancyracademy.esportsclash.schedule.applications.usecases.CancelMatchCommandHandler;
import fr.ancyracademy.esportsclash.schedule.domain.model.Moment;
import fr.ancyracademy.esportsclash.schedule.domain.model.ScheduleDay;
import fr.ancyracademy.esportsclash.schedule.infrastructure.persistence.ram.InMemoryScheduleDayRepository;
import fr.ancyracademy.esportsclash.team.domain.model.Role;
import fr.ancyracademy.esportsclash.team.domain.model.Team;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class CancelMatchTests {
  InMemoryScheduleDayRepository scheduleDayRepository = new InMemoryScheduleDayRepository();

  CancelMatchCommandHandler createHandler() {
    return new CancelMatchCommandHandler(scheduleDayRepository);
  }

  private Team createTeam(String id) {
    var name = "Team " + id;
    var team = new Team(id, name);

    team.addMember(id + "-1", Role.TOP);
    team.addMember(id + "-2", Role.JUNGLE);
    team.addMember(id + "-3", Role.MIDDLE);
    team.addMember(id + "-4", Role.BOTTOM);
    team.addMember(id + "-5", Role.SUPPORT);

    return team;
  }

  @Test
  void shouldCancelMatch() {
    var scheduleDay = new ScheduleDay("1", LocalDate.parse("2024-01-01"));

    var t1 = createTeam("t1");
    var t2 = createTeam("t2");
    var t3 = createTeam("t3");
    var t4 = createTeam("t3");

    var match = scheduleDay.organize(t1, t2, Moment.MORNING);
    scheduleDay.organize(t3, t4, Moment.AFTERNOON);

    scheduleDayRepository.save(scheduleDay);

    var command = new CancelMatchCommand(match.getId());
    var handler = createHandler();
    handler.handle(command);

    var updatedScheduleDay = scheduleDayRepository.findById("1").get();
    Assert.assertFalse(
        updatedScheduleDay.getAt(Moment.MORNING).isPresent()
    );
  }

  @Test
  void whenThereIsOnlyOneMatch_shouldDeleteTheScheduleDay() {
    var scheduleDay = new ScheduleDay("1", LocalDate.parse("2024-01-01"));

    var t1 = createTeam("t1");
    var t2 = createTeam("t2");
    var match = scheduleDay.organize(t1, t2, Moment.MORNING);

    scheduleDayRepository.save(scheduleDay);

    var command = new CancelMatchCommand(match.getId());
    var handler = createHandler();
    handler.handle(command);

    var updatedScheduleDayQuery = scheduleDayRepository.findById("1");
    Assert.assertFalse(updatedScheduleDayQuery.isPresent());
  }

  @Test
  void whenMatchDoesNotExist_shouldThrow() {
    var command = new CancelMatchCommand("random-id");
    var handler = createHandler();

    var exception = Assert.assertThrows(
        NotFoundException.class,
        () -> handler.handle(command)
    );

    Assert.assertEquals("Match with the key random-id not found", exception.getMessage());
  }
}
