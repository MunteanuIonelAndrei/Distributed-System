package controller;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import service.ChatMessage;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/{userId}")  // Dynamic topic based on user ID
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage, @Header("simpSessionId") String sessionId) {
        return chatMessage;
    }

    @MessageMapping("/chat.register")
    @SendTo("/topic/public")
    public ChatMessage register(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }
}
