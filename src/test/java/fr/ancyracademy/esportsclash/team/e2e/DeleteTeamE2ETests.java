package fr.ancyracademy.esportsclash.team.e2e;

import fr.ancyracademy.esportsclash.IntegrationTests;
import fr.ancyracademy.esportsclash.team.application.ports.TeamRepository;
import fr.ancyracademy.esportsclash.team.domain.model.Team;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class DeleteTeamE2ETests extends IntegrationTests {
  Team team = new Team("123", "team");

  @Autowired
  private TeamRepository teamRepository;

  @BeforeEach
  public void setUp() {
    teamRepository.clear();
    teamRepository.save(team);
  }

  @Test
  public void shouldDeleteTeam() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.delete("/teams/" + team.getId())
            .header("Authorization", createJWT())
        )
        .andExpect(MockMvcResultMatchers.status().isNoContent())
        .andReturn();

    var teamQuery = teamRepository.findById(team.getId());

    Assert.assertFalse(teamQuery.isPresent());
  }
}
