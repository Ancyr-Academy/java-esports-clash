package fr.ancyracademy.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import fr.ancyracademy.esportsclash.core.domain.exceptions.NotFoundException;
import fr.ancyracademy.esportsclash.team.application.ports.TeamRepository;

public class DeleteTeamCommandHandler implements Command.Handler<DeleteTeamCommand, Void> {
  private final TeamRepository teamRepository;

  public DeleteTeamCommandHandler(TeamRepository teamRepository) {
    this.teamRepository = teamRepository;
  }

  @Override
  public Void handle(DeleteTeamCommand command) {
    var team = teamRepository.findById(command.getId()).orElseThrow(
        () -> new NotFoundException("Team", command.getId())
    );

    teamRepository.delete(team);

    return null;
  }
}
