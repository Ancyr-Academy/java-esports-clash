package fr.ancyracademy.esportsclash.auth;

import fr.ancyracademy.esportsclash.auth.application.infrastructure.persistence.ram.InMemoryUserRepository;
import fr.ancyracademy.esportsclash.auth.application.ports.UserRepository;
import fr.ancyracademy.esportsclash.auth.application.usecases.RegisterCommand;
import fr.ancyracademy.esportsclash.auth.application.usecases.RegisterCommandHandler;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class RegisterCommandHandlerTests {
  private UserRepository repository = new InMemoryUserRepository();

  public RegisterCommandHandler createCommandHandler() {
    return new RegisterCommandHandler(repository);
  }

  @Test
  public void shouldRegister() {
    RegisterCommand command = new RegisterCommand(
        "contact@ancyracademy.fr",
        "password"
    );

    var commandHandler = createCommandHandler();
    var response = commandHandler.handle(command);

    var actualUser = repository.findById(response.getId()).get();

    Assert.assertEquals(command.getEmailAddress(), actualUser.getEmailAddress());
    Assert.assertEquals(command.getPassword(), actualUser.getPassword());
  }
}
