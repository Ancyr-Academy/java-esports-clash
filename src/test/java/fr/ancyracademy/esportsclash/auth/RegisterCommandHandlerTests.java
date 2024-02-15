package fr.ancyracademy.esportsclash.auth;

import fr.ancyracademy.esportsclash.auth.application.infrastructure.persistence.ram.InMemoryUserRepository;
import fr.ancyracademy.esportsclash.auth.application.services.passwordhasher.BcryptPasswordHasher;
import fr.ancyracademy.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import fr.ancyracademy.esportsclash.auth.application.usecases.RegisterCommand;
import fr.ancyracademy.esportsclash.auth.application.usecases.RegisterCommandHandler;
import fr.ancyracademy.esportsclash.auth.domain.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegisterCommandHandlerTests {
  private InMemoryUserRepository repository = new InMemoryUserRepository();
  private PasswordHasher passwordHasher = new BcryptPasswordHasher();

  public RegisterCommandHandler createCommandHandler() {
    return new RegisterCommandHandler(repository, passwordHasher);
  }

  @BeforeEach
  public void setup() {
    repository.clear();
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

  @Test
  public void whenEmailAddressIsInUse_shouldThrow() {
    var existingUser = new User(
        "123",
        "contact@ancyracademy.fr",
        "azerty"
    );

    repository.save(existingUser);

    RegisterCommand command = new RegisterCommand(
        existingUser.getEmailAddress(),
        "password"
    );

    var commandHandler = createCommandHandler();
    var exception = Assert.assertThrows(
        IllegalArgumentException.class,
        () -> commandHandler.handle(command)
    );

    Assert.assertEquals(
        "Email address is already in use",
        exception.getMessage()
    );
  }
}
