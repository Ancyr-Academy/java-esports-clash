package fr.ancyracademy.esportsclash.schedule.domain;

import fr.ancyracademy.esportsclash.schedule.domain.model.Moment;
import fr.ancyracademy.esportsclash.schedule.domain.model.ScheduleDay;
import fr.ancyracademy.esportsclash.team.domain.model.Role;
import fr.ancyracademy.esportsclash.team.domain.model.Team;
import org.junit.Assert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ScheduleDayTests {
  Team createTeam(String id) {
    var name = "Team " + id;
    var team = new Team(id, name);

    team.addMember(id + "-1", Role.TOP);
    team.addMember(id + "-2", Role.JUNGLE);
    team.addMember(id + "-3", Role.MIDDLE);
    team.addMember(id + "-4", Role.BOTTOM);
    team.addMember(id + "-5", Role.SUPPORT);

    return team;
  }

  Team createIncompleteTeam(String id) {
    var name = "Team " + id;
    var team = new Team(id, name);

    team.addMember(id + "-1", Role.TOP);
    team.addMember(id + "-2", Role.JUNGLE);
    team.addMember(id + "-3", Role.MIDDLE);
    team.addMember(id + "-4", Role.BOTTOM);

    return team;
  }

  @Nested
  class OrganizeTests {
    @Test
    void shouldOrganize() {
      var t1 = createTeam("t1");
      var t2 = createTeam("t2");
      var moment = Moment.MORNING;

      var scheduleDay = new ScheduleDay("1");

      scheduleDay.organize(t1, t2, moment);

      var match = scheduleDay.getAt(Moment.MORNING);
      Assert.assertTrue(match.isPresent());
    }

    @Test
    void whenMomentIsUnavailable_shouldThrow() {
      var t1 = createTeam("t1");
      var t2 = createTeam("t2");
      var t3 = createTeam("t3");
      var t4 = createTeam("t4");

      var scheduleDay = new ScheduleDay("1");
      scheduleDay.organize(t1, t2, Moment.MORNING);

      var exception = Assert.assertThrows(
          IllegalStateException.class,
          () -> scheduleDay.organize(t3, t4, Moment.MORNING)
      );

      Assert.assertEquals("Moment MORNING is already taken", exception.getMessage());
    }

    @Test
    void whenTeamAlreadyPlaysInTheMorning_shouldFail() {
      var t1 = createTeam("t1");
      var t2 = createTeam("t2");
      var t3 = createTeam("t3");

      var scheduleDay = new ScheduleDay("1");
      scheduleDay.organize(t1, t2, Moment.MORNING);

      var exception = Assert.assertThrows(
          IllegalStateException.class,
          () -> scheduleDay.organize(t1, t3, Moment.AFTERNOON)
      );

      Assert.assertEquals("One of the teams is already playing", exception.getMessage());
    }

    @Test
    void whenTeamAlreadyPlaysInTheAfternoon_shouldFail() {
      var t1 = createTeam("t1");
      var t2 = createTeam("t2");
      var t3 = createTeam("t3");

      var scheduleDay = new ScheduleDay("1");
      scheduleDay.organize(t1, t2, Moment.AFTERNOON);

      var exception = Assert.assertThrows(
          IllegalStateException.class,
          () -> scheduleDay.organize(t1, t3, Moment.MORNING)
      );

      Assert.assertEquals("One of the teams is already playing", exception.getMessage());
    }

    @Test
    void whenFirstTeamIsIncomplete_shouldFail() {
      var t1 = createIncompleteTeam("t1");
      var t2 = createTeam("t2");

      var scheduleDay = new ScheduleDay("1");

      var exception = Assert.assertThrows(
          IllegalStateException.class,
          () -> scheduleDay.organize(t1, t2, Moment.MORNING)
      );

      Assert.assertEquals("One of the teams is incomplete", exception.getMessage());
    }

    @Test
    void whenSecondTeamIsIncomplete_shouldFail() {
      var t1 = createTeam("t1");
      var t2 = createIncompleteTeam("t2");

      var scheduleDay = new ScheduleDay("1");

      var exception = Assert.assertThrows(
          IllegalStateException.class,
          () -> scheduleDay.organize(t1, t2, Moment.MORNING)
      );

      Assert.assertEquals("One of the teams is incomplete", exception.getMessage());
    }
  }

  @Nested
  class CancelTests {
    @Test
    void shouldCancel() {
      var t1 = createTeam("t1");
      var t2 = createTeam("t2");

      var scheduleDay = new ScheduleDay("1");
      var organizedMatch = scheduleDay.organize(t1, t2, Moment.MORNING);

      scheduleDay.cancel(organizedMatch.getId());

      var match = scheduleDay.getAt(Moment.MORNING);
      Assert.assertTrue(match.isEmpty());
    }
  }
}
