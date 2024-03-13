package fr.ancyracademy.esportsclash.auth.infrastructure.persistence.jpa;

import fr.ancyracademy.esportsclash.auth.application.ports.UserRepository;
import fr.ancyracademy.esportsclash.auth.domain.model.User;
import fr.ancyracademy.esportsclash.core.infrastructure.persistence.sql.SQLBaseRepository;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class SQLUserRepository extends SQLBaseRepository<User> implements UserRepository {
  private final SQLUserAccessor userAccessor;

  public SQLUserRepository(EntityManager entityManager, SQLUserAccessor userAccessor) {
    super(entityManager);
    this.userAccessor = userAccessor;
  }

  @Override
  public Class<User> getEntityClass() {
    return User.class;
  }

  @Override
  public boolean isEmailAddressAvailable(String emailAddress) {
    return !userAccessor.existsByEmailAddress(emailAddress);
  }

  @Override
  public Optional<User> findByEmailAddress(String emailAddress) {
    return Optional.ofNullable(userAccessor.findByEmailAddress(emailAddress));
  }
}
