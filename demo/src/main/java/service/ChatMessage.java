package service;

public class ChatMessage {
    private String content;
    private String sender;
    private String type; // types: "CHAT", "JOIN", "LEAVE"

    // Constructors, Getters, and Setters
    public ChatMessage() {}

    public ChatMessage(String content, String sender, String type) {
        this.content = content;
        this.sender = sender;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
