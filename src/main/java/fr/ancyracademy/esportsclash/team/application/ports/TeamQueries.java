package fr.ancyracademy.esportsclash.team.application.ports;

import fr.ancyracademy.esportsclash.team.domain.viewmodel.TeamViewModel;

public interface TeamQueries {
  TeamViewModel getTeamById(String id);
}
