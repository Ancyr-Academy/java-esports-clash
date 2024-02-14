package fr.ancyracademy.esportsclash.player.infrastructure.spring;

import an.awesome.pipelinr.Pipeline;
import fr.ancyracademy.esportsclash.player.application.usecases.CreatePlayerCommand;
import fr.ancyracademy.esportsclash.player.domain.viewmodel.IdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/players")
public class PlayerController {
  private final Pipeline pipeline;

  public PlayerController(Pipeline pipeline) {
    this.pipeline = pipeline;
  }

  @PostMapping
  public ResponseEntity<IdResponse> createPlayer(@RequestBody CreatePlayerDTO dto) {
    var result = this.pipeline.send(new CreatePlayerCommand(dto.getName()));
    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }
}
