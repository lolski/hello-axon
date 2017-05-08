package com.lolski.config;

import com.lolski.domain.MessagesCommandHandler;
import com.lolski.domain.MessagesEventHandler;
import com.lolski.kafka.MessagesKafkaProducer;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageChannel;

@Configuration
public class AppWiring {
  /*
   * =====================
   * kafka config
   * =====================
   */
  @Bean
  @Autowired
  public MessagesKafkaProducer messagesKafkaProducer(@Qualifier(AppConfig.OUTPUT_CHANNEL) MessageChannel output) {
    return new MessagesKafkaProducer(output);
  }

  /*
   * =====================
   * axon config
   * =====================
   */
  @Bean
  public CommandBus commandBus() {
    return new SimpleCommandBus();
  }

  @Bean
  public EventStore eventStore() {
    return new EmbeddedEventStore(new InMemoryEventStorageEngine());
  }

  @Bean
  public EventBus eventBus() {
    return new SimpleEventBus();
  }

  /*
   * =====================
   * app wiring
   * =====================
   */
  @Bean
  @Autowired
  public MessagesEventHandler messagesEventHandler(MessagesKafkaProducer messagesKafkaProducer) { return new MessagesEventHandler(messagesKafkaProducer); }

}