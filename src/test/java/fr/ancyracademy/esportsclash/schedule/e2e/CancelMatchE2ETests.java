package fr.ancyracademy.esportsclash.schedule.e2e;

import fr.ancyracademy.esportsclash.IntegrationTests;
import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;
import fr.ancyracademy.esportsclash.player.domain.model.Player;
import fr.ancyracademy.esportsclash.schedule.applications.ports.ScheduleDayRepository;
import fr.ancyracademy.esportsclash.schedule.domain.model.Moment;
import fr.ancyracademy.esportsclash.schedule.domain.model.ScheduleDay;
import fr.ancyracademy.esportsclash.schedule.infrastructure.spring.dto.CancelMatchDTO;
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

import java.time.LocalDate;
import java.util.UUID;

public class CancelMatchE2ETests extends IntegrationTests {
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
  public void shouldCancelMatch() throws Exception {
    var t1 = createTeam("t1");
    var t2 = createTeam("t2");
    var scheduleDay = new ScheduleDay("1", LocalDate.parse("2024-01-01"));
    var match = scheduleDay.organize(t1, t2, Moment.MORNING);
    scheduleDayRepository.save(scheduleDay);

    var dto = new CancelMatchDTO(
        match.getId()
    );

    mockMvc
        .perform(
            MockMvcRequestBuilders
                .post("/schedules/cancel-match")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
                .header("Authorization", createJWT())
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

    var scheduleDayQuery = scheduleDayRepository.findById(scheduleDay.getId());

    // Since there's only one match scheduled that day,
    // The schedule day will be removed
    Assert.assertFalse(scheduleDayQuery.isPresent());
  }
}