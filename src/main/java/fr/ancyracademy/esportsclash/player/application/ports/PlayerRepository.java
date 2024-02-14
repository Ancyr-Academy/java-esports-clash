package fr.ancyracademy.esportsclash.player.application.ports;

import fr.ancyracademy.esportsclash.player.domain.model.Player;

public interface PlayerRepository {
  Player findById(String id);

  void save(Player player);
}
