package fr.ancyracademy.esportsclash.auth;

import fr.ancyracademy.esportsclash.PostgreSQLTestConfiguration;
import fr.ancyracademy.esportsclash.auth.application.ports.UserRepository;
import fr.ancyracademy.esportsclash.auth.domain.model.User;
import fr.ancyracademy.esportsclash.auth.infrastructure.spring.RegisterDTO;
import fr.ancyracademy.esportsclash.player.domain.viewmodel.IdResponse;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Import(PostgreSQLTestConfiguration.class)
@Transactional
public class RegisterE2ETests {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserRepository userRepository;

  @BeforeEach
  public void setup() {
    userRepository.clear();
  }

  @Test
  public void shouldRegisterUser() throws Exception {
    var dto = new RegisterDTO("contact@ancyracademy.fr", "azerty");

    var result = mockMvc
        .perform(MockMvcRequestBuilders.post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andReturn();

    var idResponse = objectMapper.readValue(
        result.getResponse().getContentAsString(),
        IdResponse.class
    );

    var user = userRepository.findById(idResponse.getId()).get();

    Assert.assertNotNull(user);
    Assert.assertEquals(user.getEmailAddress(), dto.getEmailAddress());
  }

  @Test
  public void whenEmailAddressIsUnavailable_shouldThrow() throws Exception {
    var existingUser = new User(
        "123",
        "contact@ancyracademy.fr",
        "azerty");

    userRepository.save(existingUser);

    var dto = new RegisterDTO(existingUser.getEmailAddress(), "azerty");

    var result = mockMvc
        .perform(MockMvcRequestBuilders.post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}
