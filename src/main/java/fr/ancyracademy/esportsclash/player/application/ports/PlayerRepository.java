package fr.ancyracademy.esportsclash.player.application.ports;

import fr.ancyracademy.esportsclash.player.domain.model.Player;

import java.util.Optional;

public interface PlayerRepository {
  Optional<Player> findById(String id);

  void save(Player player);

  void delete(Player player);
}
