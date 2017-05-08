package com.lolski.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface KafkaChannels {
  @Input
  MessageChannel input();

  @Output
  MessageChannel output();
}