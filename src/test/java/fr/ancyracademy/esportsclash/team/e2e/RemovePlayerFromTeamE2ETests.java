package fr.ancyracademy.esportsclash.team.e2e;

import fr.ancyracademy.esportsclash.IntegrationTests;
import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;
import fr.ancyracademy.esportsclash.player.domain.model.Player;
import fr.ancyracademy.esportsclash.team.application.ports.TeamRepository;
import fr.ancyracademy.esportsclash.team.domain.model.Role;
import fr.ancyracademy.esportsclash.team.domain.model.Team;
import fr.ancyracademy.esportsclash.team.infrastructure.spring.dto.RemovePlayerFromTeamDTO;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class RemovePlayerFromTeamE2ETests extends IntegrationTests {
  Team team;
  Player player;

  @Autowired
  private PlayerRepository playerRepository;

  @Autowired
  private TeamRepository teamRepository;

  @BeforeEach
  public void setUp() {
    teamRepository.clear();
    playerRepository.clear();

    team = new Team("123", "team");
    player = new Player("123", "player");

    team.addMember(player.getId(), Role.TOP);

    playerRepository.save(player);
    teamRepository.save(team);
  }

  @Test
  public void shouldRemovePlayerFromTeam() throws Exception {
    var dto = new RemovePlayerFromTeamDTO(
        player.getId(),
        team.getId()
    );

    mockMvc
        .perform(MockMvcRequestBuilders.post("/teams/remove-player-from-team")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto))
            .header("Authorization", createJWT())
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

    var updatedTeam = teamRepository.findById(team.getId()).get();

    Assert.assertFalse(updatedTeam.hasMember(player.getId(), Role.TOP));
  }
}
