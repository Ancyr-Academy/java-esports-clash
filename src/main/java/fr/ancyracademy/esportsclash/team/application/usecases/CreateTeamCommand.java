package fr.ancyracademy.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import fr.ancyracademy.esportsclash.player.domain.viewmodel.IdResponse;

public class CreateTeamCommand implements Command<IdResponse> {
  private final String name;

  public CreateTeamCommand(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
