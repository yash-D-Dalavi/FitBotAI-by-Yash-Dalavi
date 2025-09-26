package com.mit.fitbotai;

public class Message {
    // Changed from 'private' to 'public' to allow access
    public String role;
    public String content;

    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }
}