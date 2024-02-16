package fr.ancyracademy.esportsclash.player;

import fr.ancyracademy.esportsclash.IntegrationTests;
import fr.ancyracademy.esportsclash.auth.domain.model.User;
import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;
import fr.ancyracademy.esportsclash.player.domain.model.Player;
import fr.ancyracademy.esportsclash.player.domain.viewmodel.PlayerViewModel;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class GetPlayerByIdE2ETests extends IntegrationTests {
  @Autowired
  private PlayerRepository playerRepository;

  @Test
  public void shouldGetPlayerById() throws Exception {
    var user = new User("123", "contact@ancyracademy.fr", "");
    userRepository.save(user);
    var jwt = "Bearer " + jwtService.tokenize(user);

    var player = new Player("123", "player");
    playerRepository.save(player);

    var result = mockMvc
        .perform(
            MockMvcRequestBuilders
                .get("/players/" + player.getId())
                .header("Authorization", createJWT())
        )
        .andReturn();

    var viewModel = objectMapper.readValue(
        result.getResponse().getContentAsString(),
        PlayerViewModel.class
    );

    Assert.assertEquals(player.getId(), viewModel.getId());
    Assert.assertEquals(player.getName(), viewModel.getName());
  }
}
