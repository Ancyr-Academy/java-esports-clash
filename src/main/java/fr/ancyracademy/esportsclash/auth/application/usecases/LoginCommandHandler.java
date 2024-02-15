package fr.ancyracademy.esportsclash.auth.application.usecases;

import an.awesome.pipelinr.Command;
import fr.ancyracademy.esportsclash.auth.application.ports.UserRepository;
import fr.ancyracademy.esportsclash.auth.application.services.jwtservice.JwtService;
import fr.ancyracademy.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import fr.ancyracademy.esportsclash.auth.domain.viewmodel.LoggedInUserViewModel;
import fr.ancyracademy.esportsclash.core.domain.exceptions.BadRequestException;
import fr.ancyracademy.esportsclash.core.domain.exceptions.NotFoundException;

public class LoginCommandHandler implements Command.Handler<LoginCommand, LoggedInUserViewModel> {
  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final PasswordHasher passwordHasher;

  public LoginCommandHandler(UserRepository userRepository, JwtService jwtService, PasswordHasher passwordHasher) {
    this.userRepository = userRepository;
    this.jwtService = jwtService;
    this.passwordHasher = passwordHasher;
  }

  @Override
  public LoggedInUserViewModel handle(LoginCommand loginCommand) {
    var user = this.userRepository
        .findByEmailAddress(loginCommand.getEmailAddress())
        .orElseThrow(() -> new NotFoundException("User"));

    var match = this.passwordHasher.match(
        loginCommand.getPassword(),
        user.getPasswordHash()
    );

    if (!match) {
      throw new BadRequestException("Invalid password");
    }

    var token = this.jwtService.tokenize(user);

    return new LoggedInUserViewModel(
        user.getId(),
        user.getEmailAddress(),
        token
    );
  }
}
