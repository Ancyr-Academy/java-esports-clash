package fr.ancyracademy.esportsclash.player.infrastructure.persistence.jpa;

import fr.ancyracademy.esportsclash.core.infrastructure.persistence.sql.SQLBaseRepository;
import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;
import fr.ancyracademy.esportsclash.player.domain.model.Player;
import jakarta.persistence.EntityManager;

public class SQLPlayerRepository extends SQLBaseRepository<Player> implements PlayerRepository {
  public SQLPlayerRepository(EntityManager entityManager) {
    super(entityManager);
  }

  @Override
  public Class<Player> getEntityClass() {
    return Player.class;
  }
}
