package com.lolski;

import com.lolski.events.MessageCreatedEvent;
import com.lolski.events.MessageReadEvent;
import org.axonframework.eventhandling.EventHandler;

public class MessageEventHandler {
    @EventHandler
    public void handle(MessageCreatedEvent messageCreatedEvent) {
        System.out.println("received a new message " + messageCreatedEvent.getId());
    }

    @EventHandler
    public void handle(MessageReadEvent messageReadEvent) {
        System.out.println("message " + messageReadEvent.getId() + " marked as read");
    }
}
