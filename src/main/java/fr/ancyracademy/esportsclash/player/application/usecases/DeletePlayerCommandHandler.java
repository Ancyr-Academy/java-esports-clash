package fr.ancyracademy.esportsclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import fr.ancyracademy.esportsclash.core.domain.exceptions.NotFoundException;
import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;

public class DeletePlayerCommandHandler implements Command.Handler<DeletePlayerCommand, Void> {
  private final PlayerRepository playerRepository;

  public DeletePlayerCommandHandler(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  @Override
  public Void handle(DeletePlayerCommand deletePlayerCommand) {
    var player = playerRepository.findById(deletePlayerCommand.getId()).orElseThrow(
        () -> new NotFoundException("Player", deletePlayerCommand.getId())
    );

    playerRepository.delete(player);
    
    return null;
  }
}
