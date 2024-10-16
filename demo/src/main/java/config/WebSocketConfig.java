package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new MyWebSocketHandler(), "/websocket")
                .setAllowedOrigins("*")
                .addInterceptors(new HandshakeInterceptor() {
                    @Override
                    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                        String uri = request.getURI().toString();
                        Map<String, String> queryParams = UriComponentsBuilder.fromUriString(uri).build().getQueryParams()
                                .toSingleValueMap();
                        attributes.put("LoginId", queryParams.get("LoginId"));
                        attributes.put("Role", queryParams.get("Role"));
                        return true;
                    }

                    @Override
                    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
                    }
                });
    }

    public class MyWebSocketHandler extends TextWebSocketHandler {
        private static class UserSession {
            WebSocketSession session;
            String loginId;
            String role;

            UserSession(WebSocketSession session, String loginId, String role) {
                this.session = session;
                this.loginId = loginId;
                this.role = role;
            }
        }

        private final Map<String, UserSession> sessions = new ConcurrentHashMap<>();
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            String loginId = (String) session.getAttributes().get("LoginId");
            String role = (String) session.getAttributes().get("Role");
            sessions.put(session.getId(), new UserSession(session, loginId, role));
            System.out.println("New session added: " + session.getId() + " for user " + loginId + " with role " + role);
        }

        @Override
        protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
            String payload = message.getPayload();
            System.out.println("Received message: " + payload);

            Map<String, String> messageMap = objectMapper.readValue(payload, Map.class);
            String receiver = messageMap.get("receiver");
            String content = messageMap.get("content");
            String sender = messageMap.get("sender");
            String type = messageMap.get("type");

            System.out.println("Parsed message: " + messageMap);

            if ("SEEN".equals(type)) {
                System.out.println("Handling SEEN message from " + sender + " to " + receiver);
                broadcastMessage(receiver, "SEEN", sender, receiver);
            } else if ("TYPING".equals(type) || "STOP_TYPING".equals(type)) {
                System.out.println("Handling " + type + " message from " + sender + " to " + receiver);
                broadcastMessage(receiver, type, sender, receiver);
            } else {
                // Handle normal message sending
                String messageId = UUID.randomUUID().toString();
                if ("Admin".equals(receiver)) {
                    for (UserSession userSession : sessions.values()) {
                        if ("admin".equalsIgnoreCase(userSession.role)) {
                            userSession.session.sendMessage(new TextMessage(
                                    objectMapper.writeValueAsString(Map.of(
                                            "id", messageId,
                                            "sender", sender,
                                            "content", content,
                                            "receiver", receiver
                                    ))
                            ));
                            System.out.println("Message sent to Admin: " + content);
                            break;
                        }
                    }
                } else {
                    for (UserSession userSession : sessions.values()) {
                        if (receiver.equals(userSession.loginId)) {
                            userSession.session.sendMessage(new TextMessage(
                                    objectMapper.writeValueAsString(Map.of(
                                            "id", messageId,
                                            "sender", "Admin",
                                            "content", content,
                                            "receiver", receiver
                                    ))
                            ));
                            System.out.println("Message sent to user " + receiver + ": " + content);
                            break;
                        }
                    }
                }
            }
        }

        private void broadcastMessage(String receiver, String type, String sender, String recipient) throws IOException {
            for (UserSession userSession : sessions.values()) {
                if (receiver.equals(userSession.loginId) || ("admin".equalsIgnoreCase(userSession.role)&&receiver.equals("Admin"))) {
                    userSession.session.sendMessage(new TextMessage(
                            objectMapper.writeValueAsString(Map.of(
                                    "type", type,
                                    "sender", sender,
                                    "receiver", recipient
                            ))
                    ));
                    System.out.println(type + " message sent to user " + receiver);
                    break;
                }
            }
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
            sessions.remove(session.getId());
            System.out.println("Removed session from sessions map: " + session.getId());
        }

        @Override
        public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
            System.err.println("Error in WebSocket transport: " + exception.getMessage());
            session.close(CloseStatus.SERVER_ERROR.withReason("Transport error"));
        }
    }
}
