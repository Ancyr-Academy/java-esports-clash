package fr.ancyracademy.esportsclash.team.domain.model;

import fr.ancyracademy.esportsclash.core.domain.model.BaseEntity;
import fr.ancyracademy.esportsclash.player.domain.model.Player;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "teams")
public class Team extends BaseEntity<Team> {
  @Column()
  private String name;

  @OneToMany(
      mappedBy = "team",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.EAGER
  )
  private Set<TeamMember> members;

  public Team() {
  }

  public Team(String id, String name) {
    super(id);
    this.name = name;
    this.members = new HashSet<>();
  }

  private Team(String id, String name, Set<TeamMember> members) {
    super(id);
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
        this.id,
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

  public Set<TeamMember> getMembers() {
    return members;
  }

  @Override
  public Team deepClone() {
    return new Team(
        this.id,
        this.name,
        this.members.stream().map(TeamMember::deepClone).collect(Collectors.toSet()));
  }

  @Entity
  @Table(name = "team_members")
  public static class TeamMember extends BaseEntity<TeamMember> {
    @Column(name = "player_id")
    private String playerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", insertable = false, updatable = false)
    @MapsId("playerId")
    private Player player;

    @Column(name = "team_id")
    private String teamId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", insertable = false, updatable = false)
    @MapsId("teamId")
    private Team team;

    @Column()
    @Enumerated(EnumType.STRING)
    private Role role;

    public TeamMember() {
    }

    public TeamMember(String id, String playerId, String teamId, Role role) {
      super(id);
      this.playerId = playerId;
      this.teamId = teamId;
      this.role = role;
    }

    @Override
    public TeamMember deepClone() {
      return new TeamMember(this.id, this.playerId, this.teamId, this.role);
    }

    public Player getPlayer() {
      return player;
    }

    public Role getRole() {
      return role;
    }
  }
}
