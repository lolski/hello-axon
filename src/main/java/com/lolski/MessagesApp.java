package com.lolski;

import com.lolski.domain.MessagesCommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessagesApp {
  @Autowired
  public MessagesApp(MessagesCommandHandler messagesCommandGateway) {
    this.messagesCommandGateway = messagesCommandGateway;

    String messageId = this.messagesCommandGateway.sendMessage("hello offer manager 2");
    this.messagesCommandGateway.markAsRead(messageId);
  }

  private MessagesCommandHandler messagesCommandGateway;
}
