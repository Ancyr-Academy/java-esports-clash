package fr.ancyracademy.esportsclash.schedule.domain.model;

import fr.ancyracademy.esportsclash.core.domain.model.BaseEntity;

public class Match extends BaseEntity<Match> {
  private String firstId;

  private String secondId;

  public Match(String id, String firstId, String secondId) {
    super(id);
    this.firstId = firstId;
    this.secondId = secondId;
  }

  public boolean featuresTeam(String id) {
    return firstId.equals(id) || secondId.equals(id);
  }

  @Override
  public Match deepClone() {
    return new Match(
        getId(),
        firstId,
        secondId
    );
  }
}
