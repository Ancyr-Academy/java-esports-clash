package fr.ancyracademy.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import fr.ancyracademy.esportsclash.team.domain.model.Role;

public class AddPlayerToTeamCommand implements Command<Void> {
  private final String playerId;
  private final String teamId;
  private final Role role;

  public AddPlayerToTeamCommand(String playerId, String teamId, Role role) {
    this.playerId = playerId;
    this.teamId = teamId;
    this.role = role;
  }

  public String getPlayerId() {
    return playerId;
  }

  public String getTeamId() {
    return teamId;
  }

  public Role getRole() {
    return role;
  }
}
