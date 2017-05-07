package com.lolski.domain.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class MarkMessageReadCommand {
  @TargetAggregateIdentifier
  private String id;

  public MarkMessageReadCommand(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }
}
