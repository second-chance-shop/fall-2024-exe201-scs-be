package scs.exe201.secondchanceshopbe.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import scs.exe201.secondchanceshopbe.models.entities.ChatMessage;
import scs.exe201.secondchanceshopbe.services.ChatService;

import java.util.concurrent.ExecutionException;

@RequestMapping("/api/v1/chat")
@RestController
public class ChatController {

   private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestBody ChatMessage chatMessage) throws ExecutionException, InterruptedException {
        chatService.sendMessage(chatMessage);
        return "Message sent successfully";
    }

    @GetMapping("/messages")
    public Object getMessages(@RequestParam String senderId, @RequestParam String receiverId) throws ExecutionException, InterruptedException {
        return chatService.getMessages(senderId, receiverId).get().getDocuments();
    }

}
