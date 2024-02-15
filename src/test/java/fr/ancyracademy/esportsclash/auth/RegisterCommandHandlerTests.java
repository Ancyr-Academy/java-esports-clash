package fr.ancyracademy.esportsclash.auth;

import fr.ancyracademy.esportsclash.auth.application.infrastructure.persistence.ram.InMemoryUserRepository;
import fr.ancyracademy.esportsclash.auth.application.ports.UserRepository;
import fr.ancyracademy.esportsclash.auth.application.services.passwordhasher.BcryptPasswordHasher;
import fr.ancyracademy.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import fr.ancyracademy.esportsclash.auth.application.usecases.RegisterCommand;
import fr.ancyracademy.esportsclash.auth.application.usecases.RegisterCommandHandler;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class RegisterCommandHandlerTests {
  private UserRepository repository = new InMemoryUserRepository();
  private PasswordHasher passwordHasher = new BcryptPasswordHasher();

  public RegisterCommandHandler createCommandHandler() {
    return new RegisterCommandHandler(repository, passwordHasher);
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
    Assert.assertTrue(
        passwordHasher.match(
            command.getPassword(),
            actualUser.getPassword()
        ));
  }
}
