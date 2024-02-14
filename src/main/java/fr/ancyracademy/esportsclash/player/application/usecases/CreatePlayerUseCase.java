package fr.ancyracademy.esportsclash.player.application.usecases;

import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;
import fr.ancyracademy.esportsclash.player.domain.model.Player;
import fr.ancyracademy.esportsclash.player.domain.viewmodel.IdResponse;

import java.util.UUID;

public class CreatePlayerUseCase {
  private final PlayerRepository repository;

  public CreatePlayerUseCase(PlayerRepository repository) {
    this.repository = repository;
  }

  public IdResponse execute(String name) {
    var player = new Player(
        UUID.randomUUID().toString(),
        name
    );

    repository.save(player);

    return new IdResponse(player.getId());
  }
}
