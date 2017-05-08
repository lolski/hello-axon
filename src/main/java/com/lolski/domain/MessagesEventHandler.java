package com.lolski.domain;

import com.lolski.domain.events.MessageCreatedEvent;
import com.lolski.domain.events.MessageReadEvent;
import com.lolski.kafka.MessageCreated;
import com.lolski.kafka.MessageRead;
import com.lolski.kafka.MessagesKafkaProducer;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;


public class MessagesEventHandler {
  public MessagesEventHandler(MessagesKafkaProducer messagesKafkaProducer) {
    this.kafkaProducer = messagesKafkaProducer;
  }

  @EventHandler
  public void handle(MessageCreatedEvent messageCreatedEvent) {
    kafkaProducer.send(new MessageCreated(messageCreatedEvent.getId(), messageCreatedEvent.getText()));
  }

  @EventHandler
  public void handle(MessageReadEvent messageReadEvent) {
    kafkaProducer.send(new MessageRead(messageReadEvent.getId()));
  }

  private MessagesKafkaProducer kafkaProducer;
}
