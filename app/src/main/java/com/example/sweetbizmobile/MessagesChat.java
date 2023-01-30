package com.example.sweetbizmobile;

public class MessagesChat {

    String message, sender, senderName, senderProfilePic, lastSender;
    long createdAt, timeSort;


    public MessagesChat() {
    }

    public MessagesChat(String message, String sender, String senderName, String senderProfilePic, String lastSender, long createdAt, long timeSort) {
        this.message = message;
        this.sender = sender;
        this.senderName = senderName;
        this.senderProfilePic = senderProfilePic;
        this.lastSender = lastSender;
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

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderProfilePic() {
        return senderProfilePic;
    }

    public void setSenderProfilePic(String senderProfilePic) {
        this.senderProfilePic = senderProfilePic;
    }

    public String getLastSender() {
        return lastSender;
    }

    public void setLastSender(String lastSender) {
        this.lastSender = lastSender;
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
