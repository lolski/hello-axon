package com.lolski.domain;

import com.lolski.domain.commands.CreateMessageCommand;
import com.lolski.domain.commands.MarkMessageReadCommand;
import com.lolski.domain.events.MessageCreatedEvent;
import com.lolski.domain.events.MessageReadEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

public class MessagesAggregate {
  @AggregateIdentifier
  private String id;

  public MessagesAggregate() {
    // is this necessary?
  }

  @CommandHandler
  public MessagesAggregate(CreateMessageCommand createMessageCommand) {
    apply(new MessageCreatedEvent(createMessageCommand.getId(), createMessageCommand.getText()));
  }

  @EventHandler
  public void on(MessageCreatedEvent messageCreatedEvent) {
    this.id = messageCreatedEvent.getId();
  }

  @CommandHandler
  public void markRead(MarkMessageReadCommand markMessageReadCommand) {
    apply(new MessageReadEvent(markMessageReadCommand.getId()));
  }

  @EventHandler
  public void on(MessageReadEvent messageReadEvent) {

  }
}
