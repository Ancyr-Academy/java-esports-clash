package fr.ancyracademy.esportsclash.schedule.applications.usecases;

import an.awesome.pipelinr.Command;
import fr.ancyracademy.esportsclash.player.domain.viewmodel.IdResponse;
import fr.ancyracademy.esportsclash.schedule.domain.model.Moment;

import java.time.LocalDate;

public class OrganizeMatchCommand implements Command<IdResponse> {
  private LocalDate date;

  private Moment moment;

  private String firstTeamId;

  private String secondTeamId;

  public OrganizeMatchCommand(LocalDate date, Moment moment, String firstTeamId, String secondTeamId) {
    this.date = date;
    this.moment = moment;
    this.firstTeamId = firstTeamId;
    this.secondTeamId = secondTeamId;
  }

  public LocalDate getDate() {
    return date;
  }

  public Moment getMoment() {
    return moment;
  }

  public String getFirstTeamId() {
    return firstTeamId;
  }

  public String getSecondTeamId() {
    return secondTeamId;
  }
}
