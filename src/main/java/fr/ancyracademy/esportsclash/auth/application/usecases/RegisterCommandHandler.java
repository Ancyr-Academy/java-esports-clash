package fr.ancyracademy.esportsclash.auth.application.usecases;

import an.awesome.pipelinr.Command;
import fr.ancyracademy.esportsclash.auth.application.ports.UserRepository;
import fr.ancyracademy.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import fr.ancyracademy.esportsclash.auth.domain.model.User;
import fr.ancyracademy.esportsclash.player.domain.viewmodel.IdResponse;

import java.util.UUID;

public class RegisterCommandHandler implements Command.Handler<RegisterCommand, IdResponse> {
  private final UserRepository userRepository;
  private final PasswordHasher passwordHasher;

  public RegisterCommandHandler(UserRepository userRepository, PasswordHasher passwordHasher) {
    this.userRepository = userRepository;
    this.passwordHasher = passwordHasher;
  }

  @Override
  public IdResponse handle(RegisterCommand registerCommand) {
    var user = new User(
        UUID.randomUUID().toString(),
        registerCommand.getEmailAddress(),
        passwordHasher.hash(registerCommand.getPassword())
    );

    userRepository.save(user);
    return new IdResponse(user.getId());
  }
}
