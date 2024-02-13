package fr.ancyracademy.esportsclash.player;

import java.util.HashMap;
import java.util.Map;

public class PlayerRepository {
  private Map<String, Player> players = new HashMap<>();

  public Player find(String id) {
    return players.get(id);
  }

  public void save(Player player) {
    this.players.put(player.getId(), player);
  }
}
