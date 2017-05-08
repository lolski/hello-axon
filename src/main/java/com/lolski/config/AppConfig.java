package com.lolski.config;

import com.lolski.domain.MessagesCommandHandler;
import com.lolski.domain.MessagesEventHandler;
import com.lolski.kafka.MessagesKafkaProducer;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  /*
   * =====================
   * app wiring
   * =====================
   */
  // message command handler <--> kafka consumer
  // message command handler <--> kafka producer
  @Bean
  public MessagesEventHandler messagesEventHandler() { return new MessagesEventHandler(messagesKafkaProducer()); }

  /*
   * =====================
   * kafka config
   * =====================
   */
  @Bean
  public MessagesKafkaProducer messagesKafkaProducer() { return new MessagesKafkaProducer(); }

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

}