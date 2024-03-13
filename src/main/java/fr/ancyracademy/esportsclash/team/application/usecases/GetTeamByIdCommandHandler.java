package fr.ancyracademy.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import fr.ancyracademy.esportsclash.team.application.ports.TeamQueries;
import fr.ancyracademy.esportsclash.team.domain.viewmodel.TeamViewModel;

public class GetTeamByIdCommandHandler implements Command.Handler<GetTeamByIdCommand, TeamViewModel> {
  private final TeamQueries teamQueries;

  public GetTeamByIdCommandHandler(TeamQueries teamQueries) {
    this.teamQueries = teamQueries;
  }

  @Override
  public TeamViewModel handle(GetTeamByIdCommand command) {
    return teamQueries.getTeamById(command.getId());
  }
}
