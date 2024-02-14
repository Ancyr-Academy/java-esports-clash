package fr.ancyracademy.esportsclash.player.infrastructure.persistence.ram;

import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;
import fr.ancyracademy.esportsclash.player.domain.model.Player;

import java.util.HashMap;
import java.util.Map;

public class InMemoryPlayerRepository implements PlayerRepository {
  private Map<String, Player> players = new HashMap<>();

  @Override
  public Player findById(String id) {
    return players.get(id);
  }

  @Override
  public void save(Player player) {
    this.players.put(player.getId(), player);
  }
}
