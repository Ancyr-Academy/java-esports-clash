package fr.ancyracademy.esportsclash.player;

import fr.ancyracademy.esportsclash.IntegrationTests;
import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;
import fr.ancyracademy.esportsclash.player.domain.model.Player;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


public class DeletePlayerE2ETests extends IntegrationTests {
  @Autowired
  private PlayerRepository playerRepository;

  @Test
  public void shouldDeletePlayer() throws Exception {
    var existingPlayer = new Player("123", "player");
    playerRepository.save(existingPlayer);


    mockMvc
        .perform(MockMvcRequestBuilders.delete(
            "/players/" + existingPlayer.getId()
        ).header("Authorization", createJWT()))
        .andExpect(MockMvcResultMatchers.status().isNoContent());


    var playerQuery = playerRepository.findById(existingPlayer.getId());
    Assert.assertTrue(playerQuery.isEmpty());
  }

  @Test
  public void whenPlayerDoesNotExist_shouldFail() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.delete(
            "/players/garbage"
        ).header("Authorization", createJWT()))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}
