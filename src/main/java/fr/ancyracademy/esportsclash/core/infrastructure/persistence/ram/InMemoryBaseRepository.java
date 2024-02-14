package fr.ancyracademy.esportsclash.core.infrastructure.persistence.ram;

import fr.ancyracademy.esportsclash.core.domain.model.BaseEntity;
import fr.ancyracademy.esportsclash.core.infrastructure.persistence.BaseRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class InMemoryBaseRepository<T extends BaseEntity> implements BaseRepository<T> {
  private Map<String, T> entities = new HashMap<>();

  @Override
  public Optional<T> findById(String id) {
    return Optional.ofNullable(this.entities.get(id));
  }

  @Override
  public void save(T entity) {
    this.entities.put(entity.getId(), entity);
  }

  @Override
  public void delete(T entity) {
    this.entities.remove(entity.getId());
  }
}
