package com.lolski.wiring;

import com.lolski.domain.events.MessageCreatedEvent;
import com.lolski.domain.events.MessageReadEvent;
//import com.lolski.kafka.MessageCreated;
//import com.lolski.kafka.MessageRead;
//import com.lolski.kafka.MessagesKafkaProducer;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageEventHandler {
  @Autowired
  public MessageEventHandler() {
//    this.kafkaProducer = kafkaProducer;
  }

  @EventHandler
  public void handle(MessageCreatedEvent messageCreatedEvent) {
    System.out.println("received a new message '" + messageCreatedEvent.getText() + "' with id '" + messageCreatedEvent.getId() + "'");
//    kafkaProducer.send(new MessageCreated(messageCreatedEvent.getId(), messageCreatedEvent.getText()));
  }

  @EventHandler
  public void handle(MessageReadEvent messageReadEvent) {
    System.out.println("message " + messageReadEvent.getId() + " marked as read");
//    kafkaProducer.send(new MessageRead(messageReadEvent.getId()));
  }

//  private MessagesKafkaProducer kafkaProducer;
}
