package fr.ancyracademy.esportsclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;
import fr.ancyracademy.esportsclash.player.domain.model.Player;
import fr.ancyracademy.esportsclash.player.domain.viewmodel.IdResponse;

import java.util.UUID;

public class CreatePlayerCommandHandler implements Command.Handler<CreatePlayerCommand, IdResponse> {
  private final PlayerRepository repository;

  public CreatePlayerCommandHandler(PlayerRepository repository) {
    this.repository = repository;
  }

  public IdResponse handle(CreatePlayerCommand command) {
    var player = new Player(
        UUID.randomUUID().toString(),
        command.getName()
    );

    repository.save(player);

    return new IdResponse(player.getId());
  }


}
