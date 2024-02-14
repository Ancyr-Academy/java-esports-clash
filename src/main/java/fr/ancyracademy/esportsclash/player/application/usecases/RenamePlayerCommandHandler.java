package fr.ancyracademy.esportsclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import fr.ancyracademy.esportsclash.core.domain.exceptions.NotFoundException;
import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;

public class RenamePlayerCommandHandler implements Command.Handler<RenamePlayerCommand, Void> {
  private final PlayerRepository playerRepository;

  public RenamePlayerCommandHandler(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  @Override
  public Void handle(RenamePlayerCommand renamePlayerCommand) {
    var player = playerRepository.findById(renamePlayerCommand.getId()).orElseThrow(
        () -> new NotFoundException("Player", renamePlayerCommand.getId())
    );

    player.rename(renamePlayerCommand.getName());
    playerRepository.save(player);

    return null;
  }
}
