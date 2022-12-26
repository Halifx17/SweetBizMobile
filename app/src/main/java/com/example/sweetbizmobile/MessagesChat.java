package com.example.sweetbizmobile;

public class MessagesChat {

    String message;
    String sender;
    long createdAt;
    long timeSort;

    public MessagesChat() {
    }

    public MessagesChat(String message, String sender, long createdAt, long timeSort) {
        this.message = message;
        this.sender = sender;
        this.createdAt = createdAt;
        this.timeSort = timeSort;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getTimeSort() {
        return timeSort;
    }

    public void setTimeSort(long timeSort) {
        this.timeSort = timeSort;
    }
}
