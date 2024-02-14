package fr.ancyracademy.esportsclash.player;

import fr.ancyracademy.esportsclash.PostgreSQLTestConfiguration;
import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;
import fr.ancyracademy.esportsclash.player.domain.model.Player;
import fr.ancyracademy.esportsclash.player.infrastructure.spring.RenamePlayerDTO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Import(PostgreSQLTestConfiguration.class)
public class RenamePlayerE2ETests {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private PlayerRepository playerRepository;

  @Test
  public void shouldRenamePlayer() throws Exception {
    var existingPlayer = new Player("123", "player");
    playerRepository.save(existingPlayer);

    var dto = new RenamePlayerDTO("new name");

    mockMvc
        .perform(MockMvcRequestBuilders.patch(
                "/players/" + existingPlayer.getId() + "/name"
            )
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(MockMvcResultMatchers.status().isOk());


    var player = playerRepository.findById(existingPlayer.getId()).get();
    Assert.assertEquals(dto.getName(), player.getName());
  }

  @Test
  public void whenPlayerDoesNotExist_shouldFail() throws Exception {
    var dto = new RenamePlayerDTO("new name");

    mockMvc
        .perform(MockMvcRequestBuilders.patch(
                "/players/garbage/name"
            )
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andReturn();
  }
}
