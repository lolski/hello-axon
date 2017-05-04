package com.lolski.events;

public class MessageReadEvent {
    private String id;

    public MessageReadEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
