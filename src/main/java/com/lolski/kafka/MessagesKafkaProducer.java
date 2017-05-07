package com.lolski.kafka;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.messaging.support.GenericMessage;
//
//@Configuration
//@EnableBinding(Source.class)
public class MessagesKafkaProducer {
  // produce

  public void send(MessageCreated messageCreated) {
    System.out.println("received a new message '" + messageCreated.getText() + "' with id '" + messageCreated.getId() + "'");
  }

  public void send(MessageRead messageRead) {
    System.out.println("message " + messageRead.getId() + " marked as read");
  }

}
