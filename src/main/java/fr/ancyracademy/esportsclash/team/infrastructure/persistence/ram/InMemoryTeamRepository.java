package fr.ancyracademy.esportsclash.team.infrastructure.persistence.ram;

import fr.ancyracademy.esportsclash.core.infrastructure.persistence.ram.InMemoryBaseRepository;
import fr.ancyracademy.esportsclash.team.application.ports.TeamRepository;
import fr.ancyracademy.esportsclash.team.domain.model.Team;

import java.util.Optional;

public class InMemoryTeamRepository extends InMemoryBaseRepository<Team> implements TeamRepository {
  @Override
  public Optional<Team> findByPlayerId(String playerId) {
    return entities.values().stream()
        .filter(team -> team.getMembers().stream().anyMatch(member -> member.getPlayerId().equals(playerId)))
        .findFirst();
  }
}
