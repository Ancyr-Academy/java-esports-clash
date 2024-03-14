package fr.ancyracademy.esportsclash.team.e2e;

import fr.ancyracademy.esportsclash.IntegrationTests;
import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;
import fr.ancyracademy.esportsclash.player.domain.model.Player;
import fr.ancyracademy.esportsclash.team.application.ports.TeamRepository;
import fr.ancyracademy.esportsclash.team.domain.model.Role;
import fr.ancyracademy.esportsclash.team.domain.model.Team;
import fr.ancyracademy.esportsclash.team.infrastructure.spring.dto.AddPlayerToTeamDTO;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class AddPlayerToTeamE2ETests extends IntegrationTests {
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

    teamRepository.save(team);
    playerRepository.save(player);
  }

  @Test
  public void shouldAddPlayerToTeam() throws Exception {
    var dto = new AddPlayerToTeamDTO(
        player.getId(),
        team.getId(),
        "TOP"
    );

    mockMvc
        .perform(MockMvcRequestBuilders.post("/teams/add-player-to-team")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto))
            .header("Authorization", createJWT())
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

    var updatedTeam = teamRepository.findById(team.getId()).get();

    Assert.assertTrue(updatedTeam.hasMember(player.getId(), Role.TOP));
  }

  @Test
  public void whenPlayerIsAlreadyInAnotherTeam_shouldThrow() throws Exception {
    var anotherTeam = new Team("456", "anotherTeam");
    anotherTeam.addMember(player.getId(), Role.TOP);
    teamRepository.save(anotherTeam);

    var dto = new AddPlayerToTeamDTO(
        player.getId(),
        team.getId(),
        "TOP"
    );

    mockMvc
        .perform(MockMvcRequestBuilders.post("/teams/add-player-to-team")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto))
            .header("Authorization", createJWT())
        )
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andReturn();
  }
}
