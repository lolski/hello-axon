package com.lolski.kafka;


import com.lolski.AnsiColor;
import com.lolski.config.AppConfig;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;

// TODO:
// - where is it listening from?
// -

@EnableBinding(KafkaChannels.class)
public class MessagesKafkaConsumer {
  @StreamListener(AppConfig.INPUT_CHANNEL)
  public void receive(Message<String> message) {
    System.out.println(AnsiColor.ANSI_YELLOW + "receiving kafka message - " + message + AnsiColor.ANSI_RESET);
  }
}
