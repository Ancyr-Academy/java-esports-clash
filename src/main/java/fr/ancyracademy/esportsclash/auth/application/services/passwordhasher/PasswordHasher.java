package fr.ancyracademy.esportsclash.auth.application.services.passwordhasher;

public interface PasswordHasher {
  String hash(String password);

  boolean match(String clearPassword, String hashedPassword);
}
