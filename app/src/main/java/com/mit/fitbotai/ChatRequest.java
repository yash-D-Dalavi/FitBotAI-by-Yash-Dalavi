package com.mit.fitbotai;
import java.util.List;

public class ChatRequest {
    private String model;
    private List<Message> messages;

    public ChatRequest(String model, List<Message> messages) {
        this.model = model;
        this.messages = messages;
    }
}