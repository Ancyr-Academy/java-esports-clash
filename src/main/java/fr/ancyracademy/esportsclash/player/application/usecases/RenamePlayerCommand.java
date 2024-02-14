package fr.ancyracademy.esportsclash.player.application.usecases;

import an.awesome.pipelinr.Command;

public class RenamePlayerCommand implements Command<Void> {
  private final String id;
  private final String name;

  public RenamePlayerCommand(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
