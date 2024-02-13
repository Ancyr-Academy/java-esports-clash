package fr.ancyracademy.esportsclash.player;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayerConfiguration {
  @Bean
  public PlayerRepository playerRepository() {
    return new PlayerRepository();
  }
}
