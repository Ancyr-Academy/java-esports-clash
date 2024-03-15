package fr.ancyracademy.esportsclash.schedule.e2e;

import fr.ancyracademy.esportsclash.IntegrationTests;
import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;
import fr.ancyracademy.esportsclash.player.domain.model.Player;
import fr.ancyracademy.esportsclash.player.domain.viewmodel.IdResponse;
import fr.ancyracademy.esportsclash.schedule.applications.ports.ScheduleDayRepository;
import fr.ancyracademy.esportsclash.schedule.infrastructure.spring.dto.OrganizeMatchDTO;
import fr.ancyracademy.esportsclash.team.application.ports.TeamRepository;
import fr.ancyracademy.esportsclash.team.domain.model.Role;
import fr.ancyracademy.esportsclash.team.domain.model.Team;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

public class OrganizeMatchE2ETests extends IntegrationTests {
  Team team = new Team("123", "team");

  @Autowired
  private PlayerRepository playerRepository;

  @Autowired
  private TeamRepository teamRepository;

  @Autowired
  private ScheduleDayRepository scheduleDayRepository;

  @BeforeEach
  public void setUp() {
    teamRepository.clear();
    teamRepository.save(team);
  }

  private Team createTeam(String id) {
    var name = "Team " + id;
    var team = new Team(id, name);

    for (Role role : Role.values()) {
      var player = new Player(UUID.randomUUID().toString(), "Player");
      playerRepository.save(player);

      team.addMember(player.getId(), role);
    }

    teamRepository.save(team);
    return team;
  }

  @Test
  public void shouldOrganizeMatch() throws Exception {
    var t1 = createTeam("t1");
    var t2 = createTeam("t2");

    var dto = new OrganizeMatchDTO(
        "2024-01-01",
        "MORNING",
        t1.getId(),
        t2.getId()
    );

    var result = mockMvc
        .perform(
            MockMvcRequestBuilders
                .post("/schedules/organize-match")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
                .header("Authorization", createJWT())
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

    var idResponse = objectMapper.readValue(
        result.getResponse().getContentAsString(),
        IdResponse.class
    );

    var scheduleDayQuery = scheduleDayRepository.findByDate(dto.toLocalDate());
    Assert.assertTrue(scheduleDayQuery.isPresent());

    var scheduleDay = scheduleDayQuery.get();

    var matchQuery = scheduleDay.getAt(dto.getMoment());
    Assert.assertTrue(matchQuery.isPresent());

    var match = matchQuery.get();
    Assert.assertEquals(idResponse.getId(), match.getId());
    Assert.assertTrue(match.featuresTeam(t1.getId()));
    Assert.assertTrue(match.featuresTeam(t2.getId()));
  }
}