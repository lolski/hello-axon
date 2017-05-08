package com.lolski.kafka;

import com.lolski.config.AppConfig;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface KafkaChannels {
  @Input(AppConfig.INPUT_CHANNEL)
  MessageChannel input();

  @Output(AppConfig.OUTPUT_CHANNEL)
  MessageChannel output();
}