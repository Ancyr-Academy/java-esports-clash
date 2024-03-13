package fr.ancyracademy.esportsclash.auth;

import fr.ancyracademy.esportsclash.auth.application.exceptions.EmailAddressUnavailableException;
import fr.ancyracademy.esportsclash.auth.application.ports.UserRepository;
import fr.ancyracademy.esportsclash.auth.application.services.passwordhasher.BcryptPasswordHasher;
import fr.ancyracademy.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import fr.ancyracademy.esportsclash.auth.application.usecases.RegisterCommand;
import fr.ancyracademy.esportsclash.auth.application.usecases.RegisterCommandHandler;
import fr.ancyracademy.esportsclash.auth.domain.model.User;
import fr.ancyracademy.esportsclash.auth.infrastructure.persistence.ram.InMemoryUserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegisterTests {
  private UserRepository repository = new InMemoryUserRepository();
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
            actualUser.getPasswordHash()
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

    Assert.assertThrows(
        EmailAddressUnavailableException.class,
        () -> commandHandler.handle(command)
    );
  }
}
