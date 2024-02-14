package fr.ancyracademy.esportsclash.player.infrastructure.persistence.jpa;

import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;
import fr.ancyracademy.esportsclash.player.domain.model.Player;

import java.util.Optional;

public class SQLPlayerRepository implements PlayerRepository {
  private final SQLPlayerDataAccessor dataAccessor;

  public SQLPlayerRepository(SQLPlayerDataAccessor dataAccessor) {
    this.dataAccessor = dataAccessor;
  }

  @Override
  public Optional<Player> findById(String id) {
    return dataAccessor.findById(id);
  }

  @Override
  public void save(Player player) {
    dataAccessor.save(player);
  }

  @Override
  public void delete(Player player) {
    dataAccessor.delete(player);
  }
}
