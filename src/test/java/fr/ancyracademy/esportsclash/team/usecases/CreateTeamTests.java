package fr.ancyracademy.esportsclash.team.usecases;

import fr.ancyracademy.esportsclash.team.application.usecases.CreateTeamCommand;
import fr.ancyracademy.esportsclash.team.application.usecases.CreateTeamCommandHandler;
import fr.ancyracademy.esportsclash.team.infrastructure.persistence.ram.InMemoryTeamRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class CreateTeamTests {
  InMemoryTeamRepository teamRepository = new InMemoryTeamRepository();

  CreateTeamCommandHandler createHandler() {
    return new CreateTeamCommandHandler(teamRepository);
  }

  @Test
  void shouldCreateTeam() {
    var command = new CreateTeamCommand("Team");
    var commandHandler = createHandler();

    var response = commandHandler.handle(command);

    var teamQuery = teamRepository.findById(response.getId());
    Assert.assertTrue(teamQuery.isPresent());

    var team = teamQuery.get();
    Assert.assertEquals("Team", team.getName());
  }
}
