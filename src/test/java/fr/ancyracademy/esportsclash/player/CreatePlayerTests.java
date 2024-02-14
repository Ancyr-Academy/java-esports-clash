package fr.ancyracademy.esportsclash.player;

import fr.ancyracademy.esportsclash.player.application.usecases.CreatePlayerUseCase;
import fr.ancyracademy.esportsclash.player.domain.model.Player;
import fr.ancyracademy.esportsclash.player.infrastructure.persistence.ram.InMemoryPlayerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class CreatePlayerTests {
  @Test
  void ShouldCreatePlayer() {
    var repository = new InMemoryPlayerRepository();
    var useCase = new CreatePlayerUseCase(repository);

    var result = useCase.execute("name");

    var expectedPlayer = new Player(result.getId(), "name");

    Player actualPlayer = repository.findById(expectedPlayer.getId());
    Assert.assertEquals(expectedPlayer.getName(), actualPlayer.getName());
  }
}
