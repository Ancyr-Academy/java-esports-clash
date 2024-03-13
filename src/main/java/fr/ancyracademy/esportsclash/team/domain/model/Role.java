package fr.ancyracademy.esportsclash.team.domain.model;

public enum Role {
  TOP,
  JUNGLE,
  MIDDLE,
  BOTTOM,
  SUPPORT;

  public static Role fromString(String role) {
    return switch (role) {
      case "TOP" -> Role.TOP;
      case "JUNGLE" -> Role.JUNGLE;
      case "MIDDLE" -> Role.MIDDLE;
      case "BOTTOM" -> Role.BOTTOM;
      case "SUPPORT" -> Role.SUPPORT;
      default -> throw new IllegalArgumentException("Invalid role: " + role);
    };
  }

  @Override
  public String toString() {
    return switch (this) {
      case TOP -> "TOP";
      case JUNGLE -> "JUNGLE";
      case MIDDLE -> "MIDDLE";
      case BOTTOM -> "BOTTOM";
      case SUPPORT -> "SUPPORT";
    };
  }
}
