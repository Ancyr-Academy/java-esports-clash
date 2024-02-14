package fr.ancyracademy.esportsclash.player.infrastructure.persistence.ram;

import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;
import fr.ancyracademy.esportsclash.player.domain.model.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryPlayerRepository implements PlayerRepository {
  private Map<String, Player> players = new HashMap<>();

  @Override
  public Optional<Player> findById(String id) {
    return Optional.ofNullable(this.players.get(id));
  }

  @Override
  public void save(Player player) {
    this.players.put(player.getId(), player);
  }
}
