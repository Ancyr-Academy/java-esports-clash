package fr.ancyracademy.esportsclash.auth.infrastructure.persistence.jpa;

import fr.ancyracademy.esportsclash.auth.domain.model.User;
import org.springframework.data.repository.CrudRepository;

public interface SQLUserAccessor extends CrudRepository<User, String> {
  boolean existsByEmailAddress(String emailAddress);

  User findByEmailAddress(String emailAddress);
}
