package fr.ancyracademy.esportsclash.team.e2e;

import fr.ancyracademy.esportsclash.IntegrationTests;
import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;
import fr.ancyracademy.esportsclash.player.domain.model.Player;
import fr.ancyracademy.esportsclash.team.application.ports.TeamRepository;
import fr.ancyracademy.esportsclash.team.domain.model.Role;
import fr.ancyracademy.esportsclash.team.domain.model.Team;
import fr.ancyracademy.esportsclash.team.domain.viewmodel.TeamViewModel;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class GetTeamByIdE2ETests extends IntegrationTests {
  Team team;
  Player player;

  @Autowired
  private PlayerRepository playerRepository;

  @Autowired
  private TeamRepository teamRepository;

  @BeforeEach
  public void setUp() {
    team = new Team("123", "team");
    player = new Player("123", "player");

    team.addMember(player.getId(), Role.TOP);

    playerRepository.save(player);
    teamRepository.save(team);

    clearDatabaseCache();
  }

  @Test
  public void shouldGetTheTeam() throws Exception {
    var result = mockMvc
        .perform(
            MockMvcRequestBuilders
                .get("/teams/" + team.getId())
                .header("Authorization", createJWT())
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

    var viewModel = objectMapper.readValue(
        result.getResponse().getContentAsString(),
        TeamViewModel.class
    );

    Assert.assertEquals(team.getId(), viewModel.getId());
    Assert.assertEquals(team.getName(), viewModel.getName());

    var firstMember = viewModel.getMembers().getFirst();
    Assert.assertEquals(player.getId(), firstMember.getPlayerId());
    Assert.assertEquals(player.getName(), firstMember.getPlayerName());
    Assert.assertEquals("TOP", firstMember.getRole());

  }
}
