package com.lolski.domain;

import com.lolski.domain.commands.CreateMessageCommand;
import com.lolski.domain.commands.MarkMessageReadCommand;
import com.lolski.kafka.MessagesKafkaProducer;
import org.axonframework.commandhandling.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.eventhandling.AnnotationEventListenerAdapter;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.lolski.domain.Helper.*;

@Component
public class MessagesCommandHandler {

  @Autowired
  public MessagesCommandHandler(CommandBus commandBus, EventStore eventStore, MessagesEventHandler annotatedEventListener) {
    EventSourcingRepository<MessagesAggregate> repository = newEventSourcingRepository(MessagesAggregate.class, eventStore);
    AggregateAnnotationCommandHandler<MessagesAggregate> aggregateAnnotationCommandHandler =
        newAggregateAnnotationCommandHandler(repository, MessagesAggregate.class);
    AnnotationEventListenerAdapter annotationEventListenerAdapter = newAnnotationEventListenerAdapter(annotatedEventListener);

    wireEventStoreToAnnotationEventListenerAdapter(eventStore, annotationEventListenerAdapter);
    wireCommandHandlerToCommandBus(aggregateAnnotationCommandHandler, commandBus);

    this.commandGateway = newCommandGateway(commandBus);
  }

  public String sendMessage(String text) {
    String uuid = UUID.randomUUID().toString();
    commandGateway.send(new CreateMessageCommand(uuid, text));
    return uuid;
  }

  public void markAsRead(String messageId) {
    commandGateway.send(new MarkMessageReadCommand(messageId));
  }

  private CommandGateway commandGateway;
}

/*
 * =====================
 * setup methods
 * =====================
 */

class Helper {
  public static <T> EventSourcingRepository<T> newEventSourcingRepository(Class<T> aggregateType, EventStore eventStore) {
    return new EventSourcingRepository<>(aggregateType, eventStore);
  }

  public static CommandGateway newCommandGateway(CommandBus commandBus) {
    return new DefaultCommandGateway(commandBus);
  }

  public static AnnotationEventListenerAdapter newAnnotationEventListenerAdapter(Object annotatedEventListener) {
    return new AnnotationEventListenerAdapter(annotatedEventListener);
  }

  public static <T> AggregateAnnotationCommandHandler<T> newAggregateAnnotationCommandHandler(
      EventSourcingRepository<T> repository, Class<T> aggregateType) {

    return new AggregateAnnotationCommandHandler<>(aggregateType, repository);
  }

  public static <T> void wireCommandHandlerToCommandBus(AggregateAnnotationCommandHandler<T> handler, CommandBus commandBus) {
    handler.subscribe(commandBus);
  }

  public static void wireEventStoreToAnnotationEventListenerAdapter(EventStore eventStore, AnnotationEventListenerAdapter annotationEventListenerAdapter) {
    eventStore.subscribe(eventMessages ->
        eventMessages.forEach(msg -> {
          try {
            annotationEventListenerAdapter.handle(msg);
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        })
    );
  }
}