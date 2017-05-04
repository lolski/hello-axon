package com.lolski;

import com.lolski.commands.CreateMessageCommand;
import com.lolski.commands.MarkMessageReadCommand;
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
public class HelloAxonApplication {
    public HelloAxonApplication() {
        String text = "hello offer manager 2";

        String messageId = sendMessage(text);
        markAsRead(messageId);
    }

    public String sendMessage(String text) {
        String uuid = UUID.randomUUID().toString();
        commandGateway.send(new CreateMessageCommand(uuid, text));
        return uuid;
    }

    public void markAsRead(String messageId) {
        commandGateway.send(new MarkMessageReadCommand(messageId));
    }

    @Autowired
    private CommandBus commandBus;

    private CommandGateway commandGateway = new DefaultCommandGateway(commandBus);

    private EventStore eventStore = setupEventStore();

    EventSourcingRepository<MessagesAggregate> repository =
            new EventSourcingRepository<MessagesAggregate>(MessagesAggregate.class, eventStore);

    AggregateAnnotationCommandHandler<MessagesAggregate> handler = setupHandler();

    AnnotationEventListenerAdapter annotationEventListenerAdapter =
            new AnnotationEventListenerAdapter(new MessageEventHandler());

    private AggregateAnnotationCommandHandler<MessagesAggregate> setupHandler() {
        AggregateAnnotationCommandHandler<MessagesAggregate> handler =
                new AggregateAnnotationCommandHandler<MessagesAggregate>(MessagesAggregate.class, repository);
        handler.subscribe(commandBus);
        return handler;
    }

    private EventStore setupEventStore() {
        EventStore eventStore = new EmbeddedEventStore(new InMemoryEventStorageEngine());
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
