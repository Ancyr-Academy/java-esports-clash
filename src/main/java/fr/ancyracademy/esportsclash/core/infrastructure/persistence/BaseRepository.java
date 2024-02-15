package fr.ancyracademy.esportsclash.core.infrastructure.persistence;

import fr.ancyracademy.esportsclash.core.domain.model.BaseEntity;

import java.util.Optional;

public interface BaseRepository<T extends BaseEntity> {
  public Optional<T> findById(String id);

  public void save(T entity);

  public void delete(T entity);

  public void clear();
}
