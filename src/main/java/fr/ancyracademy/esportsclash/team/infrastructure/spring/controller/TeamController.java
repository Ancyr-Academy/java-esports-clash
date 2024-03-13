package fr.ancyracademy.esportsclash.team.infrastructure.spring.controller;

import an.awesome.pipelinr.Pipeline;
import fr.ancyracademy.esportsclash.player.domain.viewmodel.IdResponse;
import fr.ancyracademy.esportsclash.team.application.usecases.AddPlayerToTeamCommand;
import fr.ancyracademy.esportsclash.team.application.usecases.CreateTeamCommand;
import fr.ancyracademy.esportsclash.team.application.usecases.DeleteTeamCommand;
import fr.ancyracademy.esportsclash.team.application.usecases.RemovePlayerFromTeamCommand;
import fr.ancyracademy.esportsclash.team.infrastructure.spring.dto.AddPlayerToTeamDTO;
import fr.ancyracademy.esportsclash.team.infrastructure.spring.dto.CreateTeamDTO;
import fr.ancyracademy.esportsclash.team.infrastructure.spring.dto.RemovePlayerFromTeamDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

  @PostMapping("/add-player-to-team")
  public ResponseEntity<Void> addPlayerToTeam(@RequestBody AddPlayerToTeamDTO dto) {
    var result = this.pipeline.send(new AddPlayerToTeamCommand(
        dto.getTeamId(),
        dto.getPlayerId(),
        dto.getRole()
    ));

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @PostMapping("/remove-player-from-team")
  public ResponseEntity<Void> removePlayerFromTeam(@RequestBody RemovePlayerFromTeamDTO dto) {
    var result = this.pipeline.send(new RemovePlayerFromTeamCommand(
        dto.getTeamId(),
        dto.getPlayerId()
    ));

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTeam(@PathVariable String id) {
    var result = this.pipeline.send(new DeleteTeamCommand(id));
    return ResponseEntity.noContent().build();
  }
}
