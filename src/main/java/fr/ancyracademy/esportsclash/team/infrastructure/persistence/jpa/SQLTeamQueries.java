package fr.ancyracademy.esportsclash.team.infrastructure.persistence.jpa;

import fr.ancyracademy.esportsclash.team.application.ports.TeamQueries;
import fr.ancyracademy.esportsclash.team.domain.model.Team;
import fr.ancyracademy.esportsclash.team.domain.viewmodel.TeamViewModel;
import jakarta.persistence.EntityManager;

public class SQLTeamQueries implements TeamQueries {
  private final EntityManager entityManager;

  public SQLTeamQueries(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public TeamViewModel getTeamById(String id) {
    var query = entityManager.createQuery(
        "SELECT DISTINCT t FROM Team t " +
            "JOIN FETCH t.members m " +
            "JOIN FETCH m.player " +
            "WHERE t.id = :id", Team.class
    );

    query.setParameter("id", id);

    var result = query.getSingleResult();

    var players = result.getMembers().stream()
        .map(member -> new TeamViewModel.TeamMember(
            member.getId(),
            member.getPlayer().getId(),
            member.getPlayer().getName(),
            member.getRole().name()
        ))
        .toList();

    return new TeamViewModel(
        result.getId(),
        result.getName(),
        players
    );
  }
}
