package com.lolski.domain;

import com.lolski.domain.MessageEventHandler;
import com.lolski.domain.MessagesAggregate;
import com.lolski.domain.commands.CreateMessageCommand;
import com.lolski.domain.commands.MarkMessageReadCommand;
import org.axonframework.commandhandling.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.eventhandling.AnnotationEventListenerAdapter;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MessagesCommandGateway {

  @Autowired
  public MessagesCommandGateway(CommandBus commandBus, EventStore eventStore) {

    this.commandBus = commandBus;
    this.eventStore = setupEventStore(eventStore, setupAnnotationEventListenerAdapter());
    this.commandGateway = setupCommandGateway(commandBus);
    this.repository = setupEventSourcingRepository(MessagesAggregate.class, eventStore);
    this.aggregateAnnotationCommandHandler = setupAggregateAnnotationCommandHandler(commandBus, repository, MessagesAggregate.class);
  }

  public String sendMessage(String text) {
    String uuid = UUID.randomUUID().toString();
    commandGateway.send(new CreateMessageCommand(uuid, text));
    return uuid;
  }

  public void markAsRead(String messageId) {
    commandGateway.send(new MarkMessageReadCommand(messageId));
  }

  private CommandBus commandBus;
  private EventStore eventStore;
  private CommandGateway commandGateway;
  private EventSourcingRepository<MessagesAggregate> repository;
  private AggregateAnnotationCommandHandler<MessagesAggregate> aggregateAnnotationCommandHandler;

  /*
  *
  * setup methods
  *
  */

  private <T> EventSourcingRepository<T> setupEventSourcingRepository(Class<T> aggregateType, EventStore eventStore) {
    return new EventSourcingRepository<>(aggregateType, eventStore);
  }

  private CommandGateway setupCommandGateway(CommandBus commandBus) {
    return new DefaultCommandGateway(commandBus);
  }

  private AnnotationEventListenerAdapter setupAnnotationEventListenerAdapter() {
    return new AnnotationEventListenerAdapter(new MessageEventHandler());
  }

  private <T> AggregateAnnotationCommandHandler<T>setupAggregateAnnotationCommandHandler(CommandBus commandBus,
    EventSourcingRepository<T> repository, Class<T> aggregateType) {
    AggregateAnnotationCommandHandler<T> handler = new AggregateAnnotationCommandHandler<>(aggregateType, repository);
    handler.subscribe(commandBus);
    return handler;
  }

  private EventStore setupEventStore(EventStore eventStore, AnnotationEventListenerAdapter annotationEventListenerAdapter) {
    eventStore.subscribe(eventMessages ->
        eventMessages.forEach(msg -> {
          try {
            annotationEventListenerAdapter.handle(msg);
          }
          catch (Exception e) {
            throw new RuntimeException(e);
          }
        })
    );

    return eventStore;
  }
}
