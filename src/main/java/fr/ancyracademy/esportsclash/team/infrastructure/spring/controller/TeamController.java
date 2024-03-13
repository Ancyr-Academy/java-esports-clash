package fr.ancyracademy.esportsclash.team.infrastructure.spring.controller;

import an.awesome.pipelinr.Pipeline;
import fr.ancyracademy.esportsclash.player.domain.viewmodel.IdResponse;
import fr.ancyracademy.esportsclash.team.application.usecases.CreateTeamCommand;
import fr.ancyracademy.esportsclash.team.infrastructure.spring.dto.CreateTeamDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teams")
@Transactional
public class TeamController {
  private final Pipeline pipeline;

  public TeamController(Pipeline pipeline) {
    this.pipeline = pipeline;
  }

  @PostMapping
  public ResponseEntity<IdResponse> createTeam(@RequestBody CreateTeamDTO dto) {
    var result = this.pipeline.send(new CreateTeamCommand(dto.getName()));
    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }
}
