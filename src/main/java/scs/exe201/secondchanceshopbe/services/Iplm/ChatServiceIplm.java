package scs.exe201.secondchanceshopbe.services.Iplm;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.stereotype.Service;
import scs.exe201.secondchanceshopbe.models.entities.ChatMessage;
import scs.exe201.secondchanceshopbe.services.ChatService;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChatServiceIplm implements ChatService {
    private Firestore firestore;

    public void ChatService(Firestore firestore) {
        this.firestore = firestore;
    }

    public ChatServiceIplm(Firestore firestore) {
        this.firestore = firestore;
    }

    public void sendMessage(ChatMessage message) {
        CollectionReference chat = firestore.collection("chats");
        Map<String, Object> docData = new HashMap<>();
        docData.put("message", message.getMessage());
        docData.put("senderId", message.getSenderId());
        docData.put("receiverId", message.getReceiverId());
        docData.put("timestamp", message.getTimestamp());

        chat.add(docData);
    }

    public ApiFuture<QuerySnapshot> getMessages(String senderId, String receiverId) {
        CollectionReference chats = firestore.collection("chats");
        return chats.whereEqualTo("senderId", senderId)
                .whereEqualTo("receiverId", receiverId)
                .orderBy("timestamp").get();
    }
}
