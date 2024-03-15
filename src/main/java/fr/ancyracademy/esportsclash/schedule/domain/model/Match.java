package fr.ancyracademy.esportsclash.schedule.domain.model;

import fr.ancyracademy.esportsclash.core.domain.model.BaseEntity;
import fr.ancyracademy.esportsclash.team.domain.model.Team;
import jakarta.persistence.*;

@Entity
@Table(name = "matches")
public class Match extends BaseEntity<Match> {
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "first_id")
  @MapsId("firstId")
  private Team first;

  @Column(name = "first_id")
  private String firstId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "second_id")
  @MapsId("secondId")
  private Team second;

  @Column(name = "second_id")
  private String secondId;

  @Column(name = "schedule_day_id")
  private String scheduleDayId;

  @ManyToOne()
  @JoinColumn(name = "schedule_day_id")
  @MapsId("scheduleDayId")
  private ScheduleDay scheduleDay;

  public Match() {

  }
  
  public Match(String id, String scheduleDayId, String firstId, String secondId) {
    super(id);
    this.scheduleDayId = scheduleDayId;
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
        scheduleDayId,
        firstId,
        secondId
    );
  }
}
