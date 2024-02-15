package fr.ancyracademy.esportsclash.auth.application.services.jwtservice;

import fr.ancyracademy.esportsclash.auth.domain.model.AuthUser;
import fr.ancyracademy.esportsclash.auth.domain.model.User;

public interface JwtService {
  String tokenize(User user);

  AuthUser parse(String token);
}
