package fr.ancyracademy.esportsclash.core.domain.model;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {
  @Id
  protected String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
