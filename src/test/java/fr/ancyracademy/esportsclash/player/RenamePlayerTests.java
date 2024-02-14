package fr.ancyracademy.esportsclash.player;

import fr.ancyracademy.esportsclash.core.domain.exceptions.NotFoundException;
import fr.ancyracademy.esportsclash.player.application.usecases.RenamePlayerCommand;
import fr.ancyracademy.esportsclash.player.application.usecases.RenamePlayerCommandHandler;
import fr.ancyracademy.esportsclash.player.domain.model.Player;
import fr.ancyracademy.esportsclash.player.infrastructure.persistence.ram.InMemoryPlayerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class RenamePlayerTests {
  private final InMemoryPlayerRepository playerRepository = new InMemoryPlayerRepository();

  private RenamePlayerCommandHandler createHandler() {
    return new RenamePlayerCommandHandler(playerRepository);
  }

  @Test
  void shouldRenamePlayer() {
    var player = new Player("123", "old name");
    playerRepository.save(player);

    var command = new RenamePlayerCommand(
        "123",
        "new name"
    );

    var commandHandler = createHandler();
    commandHandler.handle(command);

    Player actualPlayer = playerRepository.findById(player.getId()).get();
    Assert.assertEquals(command.getName(), actualPlayer.getName());
  }

  @Test
  void whenPlayerDoesNotExist_shouldThrowNotFound() {
    var command = new RenamePlayerCommand(
        "garbage",
        "new name"
    );
    
    var commandHandler = createHandler();

    var exception = Assert.assertThrows(
        NotFoundException.class,
        () -> commandHandler.handle(command)
    );

    Assert.assertEquals("Player with the key garbage not found", exception.getMessage());
  }
}
