package fr.ancyracademy.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;

public class RemovePlayerFromTeamCommand implements Command<Void> {
  private final String playerId;
  private final String teamId;

  public RemovePlayerFromTeamCommand(String playerId, String teamId) {
    this.playerId = playerId;
    this.teamId = teamId;
  }

  public String getPlayerId() {
    return playerId;
  }

  public String getTeamId() {
    return teamId;
  }
}
