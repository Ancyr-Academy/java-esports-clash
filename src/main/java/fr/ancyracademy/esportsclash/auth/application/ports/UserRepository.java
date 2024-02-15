package fr.ancyracademy.esportsclash.auth.application.ports;

import fr.ancyracademy.esportsclash.auth.domain.model.User;
import fr.ancyracademy.esportsclash.core.infrastructure.persistence.BaseRepository;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {
  boolean isEmailAddressAvailable(String emailAddress);

  Optional<User> findByEmailAddress(String emailAddress);
}
