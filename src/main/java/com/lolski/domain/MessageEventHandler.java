package com.lolski.domain;

import com.lolski.domain.events.MessageCreatedEvent;
import com.lolski.domain.events.MessageReadEvent;
import org.axonframework.eventhandling.EventHandler;

public class MessageEventHandler {
    @EventHandler
    public void handle(MessageCreatedEvent messageCreatedEvent) {
        System.out.println("received a new message '" + messageCreatedEvent.getText() + "' with id '" + messageCreatedEvent.getId() + "'");
    }

    @EventHandler
    public void handle(MessageReadEvent messageReadEvent) {
        System.out.println("message " + messageReadEvent.getId() + " marked as read");
    }
}
