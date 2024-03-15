package fr.ancyracademy.esportsclash.schedule.applications.usecases;

import an.awesome.pipelinr.Command;
import fr.ancyracademy.esportsclash.core.domain.exceptions.NotFoundException;
import fr.ancyracademy.esportsclash.player.domain.viewmodel.IdResponse;
import fr.ancyracademy.esportsclash.schedule.applications.ports.ScheduleDayRepository;
import fr.ancyracademy.esportsclash.schedule.domain.model.ScheduleDay;
import fr.ancyracademy.esportsclash.team.application.ports.TeamRepository;

import java.util.UUID;

public class OrganizeMatchCommandHandler implements Command.Handler<OrganizeMatchCommand, IdResponse> {
  private final TeamRepository teamRepository;

  private final ScheduleDayRepository scheduleDayRepository;

  public OrganizeMatchCommandHandler(ScheduleDayRepository scheduleDayRepository, TeamRepository teamRepository) {
    this.scheduleDayRepository = scheduleDayRepository;
    this.teamRepository = teamRepository;
  }

  @Override
  public IdResponse handle(OrganizeMatchCommand command) {
    var t1 = teamRepository.findById(command.getFirstTeamId()).orElseThrow(
        () -> new NotFoundException("Team", command.getFirstTeamId())
    );

    var t2 = teamRepository.findById(command.getSecondTeamId()).orElseThrow(
        () -> new NotFoundException("Team", command.getSecondTeamId())
    );

    var scheduleDay = scheduleDayRepository
        .findByDate(command.getDate())
        .orElse(
            new ScheduleDay(UUID.randomUUID().toString(), command.getDate())
        );

    var organizedMatch = scheduleDay.organize(t1, t2, command.getMoment());
    scheduleDayRepository.save(scheduleDay);

    return new IdResponse(organizedMatch.getId());
  }
}
