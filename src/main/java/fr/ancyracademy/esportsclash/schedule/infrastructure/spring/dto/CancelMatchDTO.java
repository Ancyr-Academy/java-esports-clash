package fr.ancyracademy.esportsclash.schedule.infrastructure.spring.dto;

public class CancelMatchDTO {
  private String matchId;

  public CancelMatchDTO() {
  }

  public CancelMatchDTO(String matchId) {
    this.matchId = matchId;
  }

  public String getMatchId() {
    return matchId;
  }
}
