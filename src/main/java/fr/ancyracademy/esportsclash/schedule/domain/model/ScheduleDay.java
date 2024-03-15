package fr.ancyracademy.esportsclash.schedule.domain.model;

import fr.ancyracademy.esportsclash.core.domain.model.BaseEntity;
import fr.ancyracademy.esportsclash.team.domain.model.Team;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ScheduleDay extends BaseEntity<ScheduleDay> {
  private LocalDate day;

  private Map<Moment, Match> matches;

  public ScheduleDay(String id) {
    super(id);
    matches = new EnumMap<>(Moment.class);
  }

  public Match organize(Team t1, Team t2, Moment moment) {
    if (matches.containsKey(moment)) {
      throw new IllegalStateException("Moment " + moment + " is already taken");
    }

    var anyTeamAlreadyPlays = matches.values().stream()
        .anyMatch(match -> match.featuresTeam(t1.getId()) || match.featuresTeam(t2.getId()));

    if (anyTeamAlreadyPlays) {
      throw new IllegalStateException("One of the teams is already playing");
    }

    if (!t1.isComplete() || !t2.isComplete()) {
      throw new IllegalStateException("One of the teams is incomplete");
    }

    var match = new Match(
        UUID.randomUUID().toString(),
        t1.getId(),
        t2.getId()
    );

    matches.put(moment, match);

    return match;
  }

  public void cancel(String matchId) {
    var moment = matches.keySet().stream()
        .filter(m -> matches.get(m).getId().equals(matchId))
        .findFirst();

    moment.ifPresent(matches::remove);
  }

  public Optional<Moment> getAt(Moment moment) {
    return matches.containsKey(moment) ?
        Optional.of(moment) :
        Optional.empty();
  }

  @Override
  public ScheduleDay deepClone() {
    return null;
  }
}
