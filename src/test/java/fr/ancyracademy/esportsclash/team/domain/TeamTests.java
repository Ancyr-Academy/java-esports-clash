package fr.ancyracademy.esportsclash.team.domain;

import fr.ancyracademy.esportsclash.team.domain.model.Role;
import fr.ancyracademy.esportsclash.team.domain.model.Team;
import org.junit.Assert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class TeamTests {
  @Nested
  class AddMember {
    @Test
    void shouldJoinTeam() {
      var team = new Team("123", "Team 1");
      team.addMember("player1", Role.TOP);

      Assert.assertTrue(team.hasMember("player1", Role.TOP));
    }

    @Test
    void whenThePlayerIsAlreadyInTheTeam_shouldThrow() {
      var team = new Team("123", "Team 1");
      team.addMember("player1", Role.TOP);

      var exception = Assert.assertThrows(
          IllegalArgumentException.class,
          () -> team.addMember("player1", Role.BOTTOM)
      );

      Assert.assertEquals("Player already in team", exception.getMessage());
    }

    @Test
    void whenTheRoleIsAlreadyTaken_shouldThrow() {
      var team = new Team("123", "Team 1");
      team.addMember("player1", Role.TOP);

      var exception = Assert.assertThrows(
          IllegalArgumentException.class,
          () -> team.addMember("player2", Role.TOP)
      );

      Assert.assertEquals("Role is already taken", exception.getMessage());
    }
  }

  @Nested
  class RemoveMember {
    @Test
    void shouldRemoveMember() {
      var team = new Team("123", "Team 1");
      team.addMember("player1", Role.TOP);
      team.removeMember("player1");

      Assert.assertFalse(team.hasMember("player1", Role.TOP));
    }

    @Test
    void whenPlayerIsNotInTheTeam_shouldThrow() {
      var team = new Team("123", "Team 1");

      var exception = Assert.assertThrows(
          IllegalArgumentException.class,
          () -> team.removeMember("player1")
      );

      Assert.assertEquals("Player not in team", exception.getMessage());
    }

    @Test
    void whenTeamHas4Members_shouldNotBeComplete() {
      var team = new Team("123", "Team 1");
      team.addMember("1", Role.TOP);
      team.addMember("2", Role.JUNGLE);
      team.addMember("3", Role.MIDDLE);
      team.addMember("4", Role.BOTTOM);

      Assert.assertFalse(team.isComplete());
    }

    @Test
    void whenTeamHas5Members_shouldNotBeComplete() {
      var team = new Team("123", "Team 1");
      team.addMember("1", Role.TOP);
      team.addMember("2", Role.JUNGLE);
      team.addMember("3", Role.MIDDLE);
      team.addMember("4", Role.BOTTOM);
      team.addMember("5", Role.SUPPORT);

      Assert.assertTrue(team.isComplete());
    }
  }
}
