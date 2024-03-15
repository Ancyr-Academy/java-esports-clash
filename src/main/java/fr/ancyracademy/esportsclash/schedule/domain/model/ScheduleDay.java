package fr.ancyracademy.esportsclash.schedule.domain.model;

import fr.ancyracademy.esportsclash.core.domain.model.BaseEntity;
import fr.ancyracademy.esportsclash.team.domain.model.Team;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "schedule_days")
public class ScheduleDay extends BaseEntity<ScheduleDay> {
  @Column(name = "day")
  private LocalDate day;

  @OneToMany(
      mappedBy = "scheduleDay",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.EAGER
  )
  @MapKeyEnumerated(EnumType.STRING)
  private Map<Moment, Match> matches;

  public ScheduleDay() {

  }

  public ScheduleDay(String id, LocalDate day) {
    super(id);
    this.day = day;
    this.matches = new EnumMap<>(Moment.class);
  }

  public ScheduleDay(String id, LocalDate day, Map<Moment, Match> matches) {
    super(id);
    this.day = day;
    this.matches = matches;
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
        this.getId(),
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

  public Optional<Match> getAt(Moment moment) {
    return matches.containsKey(moment) ?
        Optional.of(matches.get(moment)) :
        Optional.empty();
  }

  public boolean containsMatch(String matchId) {
    return matches
        .values()
        .stream()
        .anyMatch(match -> match.getId().equals(matchId));
  }

  public boolean hasMatches() {
    return !matches.isEmpty();
  }

  public LocalDate getDay() {
    return day;
  }

  @Override
  public ScheduleDay deepClone() {
    return new ScheduleDay(
        getId(),
        day,
        matches
            .keySet()
            .stream()
            .collect(
                () -> new EnumMap<>(Moment.class),
                (map, moment) -> map.put(moment, matches.get(moment).deepClone()),
                Map::putAll
            )
    );
  }
}
