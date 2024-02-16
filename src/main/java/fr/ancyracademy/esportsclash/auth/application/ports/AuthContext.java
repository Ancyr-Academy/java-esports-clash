package fr.ancyracademy.esportsclash.auth.application.ports;

import fr.ancyracademy.esportsclash.auth.domain.model.AuthUser;

import java.util.Optional;

public interface AuthContext {
  boolean isAuthenticated();

  Optional<AuthUser> getUser();
}
