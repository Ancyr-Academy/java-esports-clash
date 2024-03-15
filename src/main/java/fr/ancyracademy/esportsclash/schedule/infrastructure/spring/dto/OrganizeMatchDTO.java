package fr.ancyracademy.esportsclash.schedule.infrastructure.spring.dto;

import fr.ancyracademy.esportsclash.schedule.domain.model.Moment;

import java.time.LocalDate;

public class OrganizeMatchDTO {
  private String date;
  private String moment;
  private String firstTeamId;
  private String secondTeamId;

  public OrganizeMatchDTO() {
  }

  public OrganizeMatchDTO(String date, String moment, String firstTeamId, String secondTeamId) {
    this.date = date;
    this.moment = moment;
    this.firstTeamId = firstTeamId;
    this.secondTeamId = secondTeamId;
  }

  public String getDate() {
    return date;
  }

  public LocalDate toLocalDate() {
    return LocalDate.parse(date);
  }

  public Moment getMoment() {
    return Moment.fromString(moment);
  }

  public String getFirstTeamId() {
    return firstTeamId;
  }

  public String getSecondTeamId() {
    return secondTeamId;
  }
}
