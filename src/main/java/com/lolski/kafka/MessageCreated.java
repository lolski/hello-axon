package com.lolski.kafka;

public class MessageCreated {
  public MessageCreated(String id, String text) {
    this.id = id;
    this.text = text;
  }

  public String getId() {
    return id;
  }

  public String getText() {
    return text;
  }

  private String id;
  private String text;
}
