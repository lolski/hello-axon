package com.lolski.kafka;

import com.lolski.AnsiColor;

public class MessagesKafkaProducer {
  // produce

  public void send(MessageCreated messageCreated) {
    System.out.println(AnsiColor.ANSI_YELLOW + "received a new message '" + messageCreated.getText() + "' with id '" + messageCreated.getId() + "'" + AnsiColor.ANSI_RESET);
  }

  public void send(MessageRead messageRead) {
    System.out.println(AnsiColor.ANSI_YELLOW + "message " + messageRead.getId() + " marked as read" + AnsiColor.ANSI_RESET);
  }

}
