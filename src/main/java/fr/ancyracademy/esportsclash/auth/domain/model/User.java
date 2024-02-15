package fr.ancyracademy.esportsclash.auth.domain.model;

import fr.ancyracademy.esportsclash.core.domain.model.BaseEntity;

public class User extends BaseEntity {
  private String emailAddress;

  private String passwordHash;

  public User() {
  }

  public User(String id, String emailAddress, String passwordHash) {
    super(id);
    this.emailAddress = emailAddress;
    this.passwordHash = passwordHash;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public String getPasswordHash() {
    return passwordHash;
  }
}
