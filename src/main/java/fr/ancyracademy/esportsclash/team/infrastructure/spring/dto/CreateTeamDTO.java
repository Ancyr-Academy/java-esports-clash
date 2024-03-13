package fr.ancyracademy.esportsclash.team.infrastructure.spring.dto;

public class CreateTeamDTO {
  private String name;

  public CreateTeamDTO() {
  }

  public CreateTeamDTO(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
