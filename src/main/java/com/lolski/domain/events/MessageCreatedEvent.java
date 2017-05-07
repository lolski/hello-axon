package com.lolski.domain.events;

public class MessageCreatedEvent {
    private String id;
    private String text;

    public MessageCreatedEvent(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
