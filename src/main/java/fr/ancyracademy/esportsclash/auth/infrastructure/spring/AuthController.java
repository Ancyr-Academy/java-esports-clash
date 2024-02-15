package fr.ancyracademy.esportsclash.auth.infrastructure.spring;

import an.awesome.pipelinr.Pipeline;
import fr.ancyracademy.esportsclash.auth.application.usecases.LoginCommand;
import fr.ancyracademy.esportsclash.auth.application.usecases.RegisterCommand;
import fr.ancyracademy.esportsclash.auth.domain.viewmodel.LoggedInUserViewModel;
import fr.ancyracademy.esportsclash.player.domain.viewmodel.IdResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final Pipeline pipeline;

  public AuthController(Pipeline pipeline) {
    this.pipeline = pipeline;
  }

  @PostMapping("/register")
  public ResponseEntity<IdResponse> register(@Valid @RequestBody RegisterDTO dto) {
    return new ResponseEntity(
        pipeline.send(
            new RegisterCommand(
                dto.getEmailAddress(),
                dto.getPassword()
            )
        ),
        HttpStatus.CREATED
    );
  }

  @PostMapping("/login")
  public ResponseEntity<LoggedInUserViewModel> login(@Valid @RequestBody LoginDTO dto) {
    var result = pipeline.send(
        new LoginCommand(
            dto.getEmailAddress(),
            dto.getPassword()
        )
    );

    return ResponseEntity.ok(
        result
    );
  }
}
