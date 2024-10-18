package scs.exe201.secondchanceshopbe.models.entities;

import lombok.Data;

@Data

public class ChatMessage {
    private String message;
    private String senderId;
    private String receiverId;
    private Long timestamp;

    public ChatMessage() {
    }

    public ChatMessage(String message, String senderId, String receiverId, Long timestamp) {
        this.message = message;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.timestamp = timestamp;
    }

    // Getters and Setters
}
