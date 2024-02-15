package fr.ancyracademy.esportsclash.auth.domain.model;

import fr.ancyracademy.esportsclash.core.domain.model.BaseEntity;

public class User extends BaseEntity {
  private String emailAddress;

  private String password;

  public User() {
  }

  public User(String id, String emailAddress, String password) {
    super(id);
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
