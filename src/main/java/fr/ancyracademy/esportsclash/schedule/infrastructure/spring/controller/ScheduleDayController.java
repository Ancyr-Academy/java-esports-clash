package fr.ancyracademy.esportsclash.schedule.infrastructure.spring.controller;

import an.awesome.pipelinr.Pipeline;
import fr.ancyracademy.esportsclash.player.domain.viewmodel.IdResponse;
import fr.ancyracademy.esportsclash.schedule.applications.usecases.CancelMatchCommand;
import fr.ancyracademy.esportsclash.schedule.applications.usecases.OrganizeMatchCommand;
import fr.ancyracademy.esportsclash.schedule.infrastructure.spring.dto.CancelMatchDTO;
import fr.ancyracademy.esportsclash.schedule.infrastructure.spring.dto.OrganizeMatchDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
@Transactional
public class ScheduleDayController {
  private final Pipeline pipeline;

  public ScheduleDayController(Pipeline pipeline) {
    this.pipeline = pipeline;
  }


  @PostMapping("/organize-match")
  public ResponseEntity<IdResponse> organizeMatch(@RequestBody OrganizeMatchDTO dto) {
    var result = this.pipeline.send(new OrganizeMatchCommand(
        dto.toLocalDate(),
        dto.getMoment(),
        dto.getFirstTeamId(),
        dto.getSecondTeamId()
    ));

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @PostMapping("/cancel-match")
  public ResponseEntity<Void> cancelMatch(@RequestBody CancelMatchDTO dto) {
    var result = this.pipeline.send(new CancelMatchCommand(
        dto.getMatchId()
    ));

    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
