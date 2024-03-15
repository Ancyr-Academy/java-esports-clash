package fr.ancyracademy.esportsclash.schedule.usecases;

import fr.ancyracademy.esportsclash.core.domain.exceptions.NotFoundException;
import fr.ancyracademy.esportsclash.schedule.applications.usecases.OrganizeMatchCommand;
import fr.ancyracademy.esportsclash.schedule.applications.usecases.OrganizeMatchCommandHandler;
import fr.ancyracademy.esportsclash.schedule.domain.model.Moment;
import fr.ancyracademy.esportsclash.schedule.domain.model.ScheduleDay;
import fr.ancyracademy.esportsclash.schedule.infrastructure.persistence.ram.InMemoryScheduleDayRepository;
import fr.ancyracademy.esportsclash.team.domain.model.Role;
import fr.ancyracademy.esportsclash.team.domain.model.Team;
import fr.ancyracademy.esportsclash.team.infrastructure.persistence.ram.InMemoryTeamRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class OrganizeMatchTests {
  private InMemoryScheduleDayRepository scheduleDayRepository = new InMemoryScheduleDayRepository();
  private InMemoryTeamRepository teamRepository = new InMemoryTeamRepository();

  @BeforeEach
  void setUp() {
    scheduleDayRepository.clear();
    teamRepository.clear();
  }

  OrganizeMatchCommandHandler createHandler() {
    return new OrganizeMatchCommandHandler(scheduleDayRepository, teamRepository);
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
  void shouldOrganizeMatch() {
    var t1 = createTeam("t1");
    var t2 = createTeam("t2");

    teamRepository.save(t1);
    teamRepository.save(t2);

    var date = LocalDate.parse("2024-01-01");
    var command = new OrganizeMatchCommand(
        date,
        Moment.MORNING,
        t1.getId(),
        t2.getId()
    );

    var handler = createHandler();
    var response = handler.handle(command);

    var scheduleDayQuery = scheduleDayRepository.findByDate(date);
    Assert.assertTrue(scheduleDayQuery.isPresent());

    var scheduleDay = scheduleDayQuery.get();

    var matchQuery = scheduleDay.getAt(Moment.MORNING);
    Assert.assertTrue(matchQuery.isPresent());

    var match = matchQuery.get();

    Assert.assertEquals(response.getId(), match.getId());
    Assert.assertTrue(match.featuresTeam(t1.getId()));
    Assert.assertTrue(match.featuresTeam(t2.getId()));
  }

  @Test
  void whenScheduleDayAlreadyExists_shouldReuseIt() {
    var date = LocalDate.parse("2024-01-01");

    var t1 = createTeam("t1");
    var t2 = createTeam("t2");

    teamRepository.save(t1);
    teamRepository.save(t2);

    var scheduleDay = new ScheduleDay("1", date);
    scheduleDayRepository.save(scheduleDay);

    var command = new OrganizeMatchCommand(
        date,
        Moment.MORNING,
        t1.getId(),
        t2.getId()
    );

    var handler = createHandler();
    var response = handler.handle(command);

    var updatedScheduleDay = scheduleDayRepository.findById(scheduleDay.getId()).get();

    var matchQuery = updatedScheduleDay.getAt(Moment.MORNING);
    Assert.assertTrue(matchQuery.isPresent());

    var match = matchQuery.get();

    Assert.assertEquals(response.getId(), match.getId());
    Assert.assertTrue(match.featuresTeam(t1.getId()));
    Assert.assertTrue(match.featuresTeam(t2.getId()));
  }

  @Test
  void whenFirstTeamDoesNotExist_shouldThrow() {
    var t1 = createTeam("t1");
    teamRepository.save(t1);

    var date = LocalDate.parse("2024-01-01");
    var command = new OrganizeMatchCommand(
        date,
        Moment.MORNING,
        "garbage",
        t1.getId()
    );

    var handler = createHandler();

    var exception = Assert.assertThrows(
        NotFoundException.class,
        () -> handler.handle(command)
    );

    Assert.assertEquals("Team with the key garbage not found", exception.getMessage());
  }

  @Test
  void whenSecondTeamDoesNotExist_shouldThrow() {
    var t1 = createTeam("t1");
    teamRepository.save(t1);

    var date = LocalDate.parse("2024-01-01");
    var command = new OrganizeMatchCommand(
        date,
        Moment.MORNING,
        t1.getId(),
        "garbage"
    );

    var handler = createHandler();

    var exception = Assert.assertThrows(
        NotFoundException.class,
        () -> handler.handle(command)
    );

    Assert.assertEquals("Team with the key garbage not found", exception.getMessage());
  }
}
