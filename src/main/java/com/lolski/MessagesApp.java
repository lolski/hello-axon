package com.lolski;

import com.lolski.domain.MessagesCommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// TODO:
// - produce to output channel (configurable channel name)
// - consume from input channel (configurable channel name)

@Component
public class MessagesApp {
  @Autowired
  public MessagesApp(MessagesCommandHandler messagesCommandHandler) {
    this.messagesCommandHandler = messagesCommandHandler;

    String messageId = this.messagesCommandHandler.sendMessage("hello offer manager 2");
    this.messagesCommandHandler.markAsRead(messageId);
  }

  private MessagesCommandHandler messagesCommandHandler;
}
