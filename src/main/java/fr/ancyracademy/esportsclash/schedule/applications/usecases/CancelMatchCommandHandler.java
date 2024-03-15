package fr.ancyracademy.esportsclash.schedule.applications.usecases;

import an.awesome.pipelinr.Command;
import fr.ancyracademy.esportsclash.core.domain.exceptions.NotFoundException;
import fr.ancyracademy.esportsclash.schedule.applications.ports.ScheduleDayRepository;

public class CancelMatchCommandHandler implements Command.Handler<CancelMatchCommand, Void> {
  private final ScheduleDayRepository scheduleDayRepository;

  public CancelMatchCommandHandler(ScheduleDayRepository scheduleDayRepository) {
    this.scheduleDayRepository = scheduleDayRepository;
  }

  @Override
  public Void handle(CancelMatchCommand command) {
    var scheduleDay = scheduleDayRepository
        .findByMatchId(command.getMatchId())
        .orElseThrow(
            () -> new NotFoundException("Match", command.getMatchId()
            ));

    scheduleDay.cancel(command.getMatchId());

    if (scheduleDay.hasMatches()) {
      scheduleDayRepository.save(scheduleDay);
    } else {
      scheduleDayRepository.delete(scheduleDay);
    }

    return null;
  }
}
