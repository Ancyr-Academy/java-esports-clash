package fr.ancyracademy.esportsclash.player;

import java.util.UUID;

public class CreatePlayerUseCase {
  private final PlayerRepository repository;

  CreatePlayerUseCase(PlayerRepository repository) {
    this.repository = repository;
  }

  String execute(String name) {
    var player = new Player(
        UUID.randomUUID().toString(),
        name
    );

    repository.save(player);

    return player.getId();
  }
}
