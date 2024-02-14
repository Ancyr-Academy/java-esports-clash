package fr.ancyracademy.esportsclash.player;

import fr.ancyracademy.esportsclash.core.domain.exceptions.NotFoundException;
import fr.ancyracademy.esportsclash.player.application.usecases.DeletePlayerCommand;
import fr.ancyracademy.esportsclash.player.application.usecases.DeletePlayerCommandHandler;
import fr.ancyracademy.esportsclash.player.domain.model.Player;
import fr.ancyracademy.esportsclash.player.infrastructure.persistence.ram.InMemoryPlayerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class DeletePlayerTests {
  private final InMemoryPlayerRepository playerRepository = new InMemoryPlayerRepository();

  private DeletePlayerCommandHandler createHandler() {
    return new DeletePlayerCommandHandler(playerRepository);
  }

  @Test
  void shouldDeletePlayer() {
    var player = new Player("123", "old name");
    playerRepository.save(player);

    var command = new DeletePlayerCommand(
        "123"
    );

    var commandHandler = createHandler();
    commandHandler.handle(command);

    var playerQuery = playerRepository.findById(player.getId());
    Assert.assertTrue(playerQuery.isEmpty());
  }

  @Test
  void whenPlayerDoesNotExist_shouldThrowNotFound() {
    var command = new DeletePlayerCommand(
        "garbage"
    );

    var commandHandler = createHandler();

    var exception = Assert.assertThrows(
        NotFoundException.class,
        () -> commandHandler.handle(command)
    );

    Assert.assertEquals("Player with the key garbage not found", exception.getMessage());
  }
}
