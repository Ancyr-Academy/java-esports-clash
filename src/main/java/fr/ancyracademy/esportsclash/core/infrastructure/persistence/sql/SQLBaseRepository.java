package fr.ancyracademy.esportsclash.core.infrastructure.persistence.sql;

import fr.ancyracademy.esportsclash.core.domain.model.BaseEntity;
import fr.ancyracademy.esportsclash.core.infrastructure.persistence.BaseRepository;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public abstract class SQLBaseRepository<T extends BaseEntity> implements BaseRepository<T> {
  private final EntityManager entityManager;

  public SQLBaseRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Optional<T> findById(String id) {
    return Optional.ofNullable(
        entityManager.find(getEntityClass(), id)
    );
  }

  @Override
  public void save(T entity) {
    entityManager.persist(entity);
  }

  @Override
  public void delete(T entity) {
    entityManager.remove(entity);
  }

  public abstract Class<T> getEntityClass();
}
