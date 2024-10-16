package monotoringSystemVersion2.monotoring.configs;

import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.stereotype.Component;

import java.util.concurrent.CopyOnWriteArrayList;

@Component
@EnableWebSocket
public class DeviceWebSocketHandler extends TextWebSocketHandler implements WebSocketConfigurer {

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // Handle received messages if necessary
    }

    public void broadcastUpdate(String message) {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (Exception e) {
                    // Handle exception
                }
            }
        }
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(this, "/ws/devices").setAllowedOrigins("*");
    }
}
