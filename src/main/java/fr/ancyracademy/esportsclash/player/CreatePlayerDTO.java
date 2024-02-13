package fr.ancyracademy.esportsclash.player;

public class CreatePlayerDTO {
  private String name;

  public CreatePlayerDTO() {
  }

  public CreatePlayerDTO(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
