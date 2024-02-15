package fr.ancyracademy.esportsclash.auth.application.infrastructure.persistence.ram;

import fr.ancyracademy.esportsclash.auth.application.ports.UserRepository;
import fr.ancyracademy.esportsclash.auth.domain.model.User;
import fr.ancyracademy.esportsclash.core.infrastructure.persistence.ram.InMemoryBaseRepository;

public class InMemoryUserRepository extends InMemoryBaseRepository<User> implements UserRepository {
  @Override
  public boolean isEmailAddressAvailable(String emailAddress) {
    return entities
        .values()
        .stream()
        .noneMatch(user -> user.getEmailAddress().equals(emailAddress));
  }
}
