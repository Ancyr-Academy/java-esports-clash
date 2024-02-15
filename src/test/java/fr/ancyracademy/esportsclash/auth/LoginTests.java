package fr.ancyracademy.esportsclash.auth;

import fr.ancyracademy.esportsclash.auth.application.infrastructure.persistence.ram.InMemoryUserRepository;
import fr.ancyracademy.esportsclash.auth.application.services.jwtservice.ConcreteJwtService;
import fr.ancyracademy.esportsclash.auth.application.services.passwordhasher.BcryptPasswordHasher;
import fr.ancyracademy.esportsclash.auth.application.usecases.LoginCommand;
import fr.ancyracademy.esportsclash.auth.application.usecases.LoginCommandHandler;
import fr.ancyracademy.esportsclash.auth.domain.model.User;
import fr.ancyracademy.esportsclash.auth.domain.viewmodel.LoggedInUserViewModel;
import fr.ancyracademy.esportsclash.core.domain.exceptions.BadRequestException;
import fr.ancyracademy.esportsclash.core.domain.exceptions.NotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoginTests {
  private final InMemoryUserRepository userRepository = new InMemoryUserRepository();
  private final ConcreteJwtService jwtService = new ConcreteJwtService(
      "supersekretpleasechangeitwithsomethingbetter",
      60
  );

  private final User user = new User(
      "123",
      "contact@ancyracademy.fr",
      new BcryptPasswordHasher().hash("azerty")
  );

  LoginCommandHandler createHandler() {
    return new LoginCommandHandler(
        userRepository,
        jwtService,
        new BcryptPasswordHasher()
    );
  }

  @BeforeEach
  void setUp() {
    userRepository.clear();
    userRepository.save(user);
  }

  @Nested
  class HappyPath {
    @Test
    void shouldReturnTheUser() {
      var command = new LoginCommand("contact@ancyracademy.fr", "azerty");
      var commandHandler = createHandler();

      LoggedInUserViewModel result = commandHandler.handle(command);

      Assert.assertEquals(
          user.getId(),
          result.getId()
      );

      Assert.assertEquals(
          user.getEmailAddress(),
          result.getEmailAddress()
      );

      var authenticatedUser = jwtService.parse(result.getToken());

      Assert.assertEquals(
          user.getId(),
          authenticatedUser.getId()
      );

      Assert.assertEquals(
          user.getEmailAddress(),
          authenticatedUser.getEmailAddress()
      );
    }
  }

  @Nested
  class Scenario_TheEmailAddressIsIncorrect {
    @Test
    void shouldThrowNotFound() {
      var command = new LoginCommand("johndoe@ancyracademy.fr", "password");
      var commandHandler = createHandler();

      assertThrows(
          NotFoundException.class,
          () -> commandHandler.handle(command)
      );
    }
  }

  @Nested
  class Scenario_ThePasswordIsIncorrect {
    @Test
    void shouldThrowNotFound() {
      var command = new LoginCommand("contact@ancyracademy.fr", "not the correct password");
      var commandHandler = createHandler();

      var exception = assertThrows(
          BadRequestException.class,
          () -> commandHandler.handle(command)
      );

      Assert.assertEquals(
          "Invalid password",
          exception.getMessage()
      );
    }
  }
}
