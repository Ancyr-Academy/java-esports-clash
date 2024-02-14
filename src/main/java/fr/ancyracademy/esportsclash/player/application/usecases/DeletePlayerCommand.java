package fr.ancyracademy.esportsclash.player.application.usecases;

import an.awesome.pipelinr.Command;

public class DeletePlayerCommand implements Command<Void> {
  private final String id;

  public DeletePlayerCommand(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }
}
