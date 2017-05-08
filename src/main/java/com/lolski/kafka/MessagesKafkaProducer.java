package com.lolski.kafka;

import com.lolski.AnsiColor;
import kafka.Kafka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(KafkaChannels.class)
public class MessagesKafkaProducer {
  // produce

  @Autowired
  public MessagesKafkaProducer(MessageChannel output) {
    this.output = output;
  }
  public void send(MessageCreated messageCreated) {
    System.out.println(AnsiColor.ANSI_YELLOW + "received a new message '" + messageCreated.getText() + "' with id '" + messageCreated.getId() + "'" + AnsiColor.ANSI_RESET);
    this.output.send(MessageBuilder.withPayload("(" + messageCreated.getId() + "," + messageCreated.getText() + ")").build());
  }

  public void send(MessageRead messageRead) {
    System.out.println(AnsiColor.ANSI_YELLOW + "message " + messageRead.getId() + " marked as read" + AnsiColor.ANSI_RESET);
    this.output.send(MessageBuilder.withPayload("(" + messageRead.getId() + ")").build());
  }

  private MessageChannel output;

}