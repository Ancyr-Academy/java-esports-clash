package fr.ancyracademy.esportsclash.auth.application.infrastructure.auth.spring;

import fr.ancyracademy.esportsclash.auth.application.ports.AuthContext;
import fr.ancyracademy.esportsclash.auth.domain.model.AuthUser;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SpringAuthContext implements AuthContext {
  @Override
  public boolean isAuthenticated() {
    return SecurityContextHolder
        .getContext()
        .getAuthentication()
        .isAuthenticated();
  }

  @Override
  public Optional<AuthUser> getUser() {
    return Optional.ofNullable(
            SecurityContextHolder
                .getContext()
                .getAuthentication()
        )
        .map(auth -> {
          if (auth.getPrincipal() instanceof AuthUser) {
            return (AuthUser) auth.getPrincipal();
          }

          return null;
        });
  }
}
