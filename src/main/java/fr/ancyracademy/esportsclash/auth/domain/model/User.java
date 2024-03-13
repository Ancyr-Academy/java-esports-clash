package fr.ancyracademy.esportsclash.auth.domain.model;

import fr.ancyracademy.esportsclash.core.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
  @Column(name = "email_address")
  private String emailAddress;

  @Column(name = "password_hash")
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
