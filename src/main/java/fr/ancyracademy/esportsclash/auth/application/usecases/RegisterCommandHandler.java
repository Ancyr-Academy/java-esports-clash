package fr.ancyracademy.esportsclash.auth.application.usecases;

import an.awesome.pipelinr.Command;
import fr.ancyracademy.esportsclash.auth.application.ports.UserRepository;
import fr.ancyracademy.esportsclash.auth.domain.model.User;
import fr.ancyracademy.esportsclash.player.domain.viewmodel.IdResponse;

import java.util.UUID;

public class RegisterCommandHandler implements Command.Handler<RegisterCommand, IdResponse> {
  private final UserRepository userRepository;

  public RegisterCommandHandler(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public IdResponse handle(RegisterCommand registerCommand) {
    var user = new User(
        UUID.randomUUID().toString(),
        registerCommand.getEmailAddress(),
        registerCommand.getPassword()
    );

    userRepository.save(user);
    return new IdResponse(user.getId());
  }
}
