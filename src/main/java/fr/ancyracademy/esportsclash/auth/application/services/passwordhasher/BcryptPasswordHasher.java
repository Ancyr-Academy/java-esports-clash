package fr.ancyracademy.esportsclash.auth.application.services.passwordhasher;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptPasswordHasher implements PasswordHasher {
  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  @Override
  public String hash(String password) {
    return encoder.encode(password);
  }

  @Override
  public boolean match(String clearPassword, String hashedPassword) {
    return encoder.matches(clearPassword, hashedPassword);
  }
}
