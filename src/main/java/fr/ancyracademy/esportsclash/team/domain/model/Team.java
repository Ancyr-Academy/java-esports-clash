package fr.ancyracademy.esportsclash.team.domain.model;

import fr.ancyracademy.esportsclash.core.domain.model.BaseEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Team extends BaseEntity<Team> {
  private String id;
  private String name;
  private Set<TeamMember> members;

  public Team(String id, String name) {
    this.id = id;
    this.name = name;
    this.members = new HashSet<>();
  }

  private Team(String id, String name, Set<TeamMember> members) {
    this.id = id;
    this.name = name;
    this.members = members;
  }

  public void addMember(String playerId, Role role) {
    if (this.members.stream().anyMatch(member -> member.playerId.equals(playerId))) {
      throw new IllegalArgumentException("Player already in team");
    }

    if (this.members.stream().anyMatch(member -> member.role == role)) {
      throw new IllegalArgumentException("Role is already taken");
    }

    var member = new TeamMember(
        UUID.randomUUID().toString(),
        playerId,
        role);

    this.members.add(member);
  }

  public void removeMember(String playerId) {
    if (this.members.stream().noneMatch(member -> member.playerId.equals(playerId))) {
      throw new IllegalArgumentException("Player not in team");
    }

    this.members.removeIf(member -> member.playerId.equals(playerId));
  }

  public boolean hasMember(String playerId, Role role) {
    return this.members
        .stream()
        .anyMatch(member -> member.playerId.equals(playerId) && member.role == role);
  }

  public String getName() {
    return name;
  }

  @Override
  public Team deepClone() {
    return new Team(
        this.id,
        this.name,
        this.members.stream().map(TeamMember::deepClone).collect(Collectors.toSet()));
  }

  class TeamMember extends BaseEntity<TeamMember> {
    private String playerId;
    private Role role;

    public TeamMember(String id, String playerId, Role role) {
      super(id);
      this.playerId = playerId;
      this.role = role;
    }

    @Override
    public TeamMember deepClone() {
      return new TeamMember(this.id, this.playerId, this.role);
    }
  }
}
