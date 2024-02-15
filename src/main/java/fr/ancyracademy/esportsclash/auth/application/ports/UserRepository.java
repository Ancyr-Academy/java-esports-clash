package fr.ancyracademy.esportsclash.auth.application.ports;

import fr.ancyracademy.esportsclash.auth.domain.model.User;
import fr.ancyracademy.esportsclash.core.infrastructure.persistence.BaseRepository;

public interface UserRepository extends BaseRepository<User> {
}
