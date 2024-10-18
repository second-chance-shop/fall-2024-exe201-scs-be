package scs.exe201.secondchanceshopbe.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.QuerySnapshot;
import scs.exe201.secondchanceshopbe.models.entities.ChatMessage;

public interface ChatService {
    void sendMessage(ChatMessage message);
    ApiFuture<QuerySnapshot> getMessages(String senderId, String receiverId);
}
