package fr.ancyracademy.esportsclash.player;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class CreatePlayerTests {
  @Test
  void ShouldCreatePlayer() {
    var repository = new PlayerRepository();
    var useCase = new CreatePlayerUseCase(repository);

    var id = useCase.execute("name");

    var expectedPlayer = new Player(id, "name");

    Player actualPlayer = repository.find(expectedPlayer.getId());
    Assert.assertEquals(expectedPlayer.getName(), actualPlayer.getName());
  }
}
