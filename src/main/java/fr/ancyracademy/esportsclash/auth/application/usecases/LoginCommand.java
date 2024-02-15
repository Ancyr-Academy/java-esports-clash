package fr.ancyracademy.esportsclash.auth.application.usecases;

import an.awesome.pipelinr.Command;
import fr.ancyracademy.esportsclash.auth.domain.viewmodel.LoggedInUserViewModel;

public class LoginCommand implements Command<LoggedInUserViewModel> {
  private String emailAddress;
  private String password;

  public LoginCommand() {

  }

  public LoginCommand(String emailAddress, String password) {
    this.emailAddress = emailAddress;
    this.password = password;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public String getPassword() {
    return password;
  }
}
