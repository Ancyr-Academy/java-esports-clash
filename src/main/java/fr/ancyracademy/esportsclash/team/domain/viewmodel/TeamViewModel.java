package fr.ancyracademy.esportsclash.team.domain.viewmodel;

import java.util.List;

public class TeamViewModel {
  private String id;
  private String name;
  private List<TeamMember> members;

  public TeamViewModel() {
  }

  public TeamViewModel(String id, String name, List<TeamMember> members) {
    this.id = id;
    this.name = name;
    this.members = members;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<TeamMember> getMembers() {
    return members;
  }

  public static class TeamMember {
    private String id;
    private String playerId;
    private String playerName;
    private String role;

    public TeamMember() {
    }

    public TeamMember(String id, String playerId, String playerName, String role) {
      this.id = id;
      this.playerId = playerId;
      this.playerName = playerName;
      this.role = role;
    }

    public String getId() {
      return id;
    }

    public String getPlayerId() {
      return playerId;
    }

    public String getPlayerName() {
      return playerName;
    }

    public String getRole() {
      return role;
    }
  }
}
