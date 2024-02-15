package fr.ancyracademy.esportsclash.auth.domain.viewmodel;

public class LoggedInUserViewModel {
  private String id;
  private String emailAddress;
  private String token;

  public LoggedInUserViewModel() {

  }

  public LoggedInUserViewModel(String id, String emailAddress, String token) {
    this.id = id;
    this.emailAddress = emailAddress;
    this.token = token;
  }

  public String getId() {
    return id;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public String getToken() {
    return token;
  }
}
