package fr.ancyracademy.esportsclash.auth.domain.model;

public class AuthUser {
  private String id;
  private String emailAddress;

  public AuthUser(String id, String emailAddress) {
    this.id = id;
    this.emailAddress = emailAddress;
  }

  public String getId() {
    return id;
  }

  public String getEmailAddress() {
    return emailAddress;
  }
}
