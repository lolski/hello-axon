package com.lolski;

import com.lolski.domain.MessagesAggregate;
import com.lolski.domain.MessagesCommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessagesApp {
  @Autowired
  public MessagesApp(MessagesCommandGateway messagesCommandGateway) {
    this.messagesCommandGateway = messagesCommandGateway;

    String messageId = messagesCommandGateway.sendMessage("hello offer manager 2");
    messagesCommandGateway.markAsRead(messageId);
  }

  private MessagesCommandGateway messagesCommandGateway;
}
