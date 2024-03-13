package fr.ancyracademy.esportsclash.team.infrastructure.persistence.jpa;

import fr.ancyracademy.esportsclash.core.infrastructure.persistence.sql.SQLBaseRepository;
import fr.ancyracademy.esportsclash.team.application.ports.TeamRepository;
import fr.ancyracademy.esportsclash.team.domain.model.Team;
import jakarta.persistence.EntityManager;

public class SQLTeamRepository extends SQLBaseRepository<Team> implements TeamRepository {
  public SQLTeamRepository(EntityManager entityManager) {
    super(entityManager);
  }

  @Override
  public Class<Team> getEntityClass() {
    return Team.class;
  }
}
