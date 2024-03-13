package fr.ancyracademy.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import fr.ancyracademy.esportsclash.core.domain.exceptions.NotFoundException;
import fr.ancyracademy.esportsclash.team.application.ports.TeamRepository;

public class RemovePlayerFromTeamCommandHandler implements Command.Handler<RemovePlayerFromTeamCommand, Void> {
  TeamRepository teamRepository;

  public RemovePlayerFromTeamCommandHandler(TeamRepository teamRepository) {
    this.teamRepository = teamRepository;
  }

  @Override
  public Void handle(RemovePlayerFromTeamCommand command) {
    var team = teamRepository
        .findById(command.getTeamId())
        .orElseThrow(
            () -> new NotFoundException("Team", command.getTeamId())
        );

    team.removeMember(command.getPlayerId());

    teamRepository.save(team);

    return null;
  }
}
