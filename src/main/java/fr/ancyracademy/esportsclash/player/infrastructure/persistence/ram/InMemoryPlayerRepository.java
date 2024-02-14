package fr.ancyracademy.esportsclash.player.infrastructure.persistence.ram;

import fr.ancyracademy.esportsclash.core.infrastructure.persistence.ram.InMemoryBaseRepository;
import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;
import fr.ancyracademy.esportsclash.player.domain.model.Player;

public class InMemoryPlayerRepository extends InMemoryBaseRepository<Player> implements PlayerRepository {

}
