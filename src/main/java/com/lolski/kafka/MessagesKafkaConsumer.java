package com.lolski.kafka;


import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;

// TODO:
// - where is it listening from?
// -

@EnableBinding
public class MessagesKafkaConsumer {
  @StreamListener(Sink.INPUT)
  public void receive(Message<Object> message) {
    System.out.println("receiving kafka message - " + message);

  }
}
