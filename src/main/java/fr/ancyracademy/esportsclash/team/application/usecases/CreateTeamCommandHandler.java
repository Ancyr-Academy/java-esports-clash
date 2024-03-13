package fr.ancyracademy.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import fr.ancyracademy.esportsclash.player.domain.viewmodel.IdResponse;
import fr.ancyracademy.esportsclash.team.application.ports.TeamRepository;
import fr.ancyracademy.esportsclash.team.domain.model.Team;

import java.util.UUID;

public class CreateTeamCommandHandler implements Command.Handler<CreateTeamCommand, IdResponse> {
  private final TeamRepository teamRepository;

  public CreateTeamCommandHandler(TeamRepository teamRepository) {
    this.teamRepository = teamRepository;
  }

  @Override
  public IdResponse handle(CreateTeamCommand createTeamCommand) {
    var team = new Team(
        UUID.randomUUID().toString(),
        createTeamCommand.getName()
    );

    teamRepository.save(team);
    
    return new IdResponse(team.getId());
  }
}
