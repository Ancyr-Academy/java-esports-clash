package fr.ancyracademy.esportsclash.team.application.ports;

import fr.ancyracademy.esportsclash.core.infrastructure.persistence.BaseRepository;
import fr.ancyracademy.esportsclash.team.domain.model.Team;

import java.util.Optional;

public interface TeamRepository extends BaseRepository<Team> {
  Optional<Team> findByPlayerId(String playerId);
}
