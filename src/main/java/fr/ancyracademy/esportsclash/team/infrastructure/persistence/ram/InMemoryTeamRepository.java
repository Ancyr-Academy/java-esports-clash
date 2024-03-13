package fr.ancyracademy.esportsclash.team.infrastructure.persistence.ram;

import fr.ancyracademy.esportsclash.core.infrastructure.persistence.ram.InMemoryBaseRepository;
import fr.ancyracademy.esportsclash.team.application.ports.TeamRepository;
import fr.ancyracademy.esportsclash.team.domain.model.Team;

public class InMemoryTeamRepository extends InMemoryBaseRepository<Team> implements TeamRepository {
}
