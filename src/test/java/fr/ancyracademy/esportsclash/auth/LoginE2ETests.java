package fr.ancyracademy.esportsclash.auth;

import fr.ancyracademy.esportsclash.IntegrationTests;
import fr.ancyracademy.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import fr.ancyracademy.esportsclash.auth.domain.model.User;
import fr.ancyracademy.esportsclash.auth.domain.viewmodel.LoggedInUserViewModel;
import fr.ancyracademy.esportsclash.auth.infrastructure.spring.LoginDTO;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


public class LoginE2ETests extends IntegrationTests {
  @Autowired
  private PasswordHasher passwordHasher;

  @BeforeEach
  public void setup() {
    userRepository.clear();

    var user = new User(
        "123",
        "contact@ancyracademy.fr",
        passwordHasher.hash("azerty"));

    userRepository.save(user);
  }

  @Test
  public void shouldLogTheUserIn() throws Exception {
    var dto = new LoginDTO("contact@ancyracademy.fr", "azerty");

    var result = mockMvc
        .perform(MockMvcRequestBuilders.post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

    LoggedInUserViewModel viewModel = objectMapper.readValue(
        result.getResponse().getContentAsString(),
        LoggedInUserViewModel.class
    );

    Assert.assertEquals("123", viewModel.getId());
    Assert.assertEquals("contact@ancyracademy.fr", viewModel.getEmailAddress());
  }

  @Test
  public void whenEmailAddressIsInvalid_shouldThrow() throws Exception {
    var dto = new LoginDTO("not-available@ancyracademy.fr", "azerty");

    var result = mockMvc
        .perform(MockMvcRequestBuilders.post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void whenPasswordIsInvalid_shouldThrow() throws Exception {
    var dto = new LoginDTO("contact@ancyracademy.fr", "123");

    var result = mockMvc
        .perform(MockMvcRequestBuilders.post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}
