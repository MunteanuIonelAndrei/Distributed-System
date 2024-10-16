import React, { useState, useEffect, useRef, useCallback } from 'react';
import { Container, Form, Button } from 'react-bootstrap';

function UserChatPage() {
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState("");
  const [seen, setSeen] = useState(false);
  const [typing, setTyping] = useState(false);
  const [adminTyping, setAdminTyping] = useState(false);
  const ws = useRef(null);
  const typingTimeoutRef = useRef(null);

  useEffect(() => {
    const connectWebSocket = () => {
      ws.current = new WebSocket(`ws://localhost:8074/websocket?LoginId=${sessionStorage.getItem("LoginId")}&Role=${sessionStorage.getItem("Role")}`);

      ws.current.onopen = () => {
        console.log('WebSocket connection established');
      };

      ws.current.onmessage = (event) => {
        try {
          const message = JSON.parse(event.data);
          console.log("Message received: ", message);

          if (message.type === "SEEN") {
            console.log(`Messages seen by ${message.sender}`);
            setSeen(true);
          } else if (message.type === "TYPING") {
            if (message.sender === "Admin") {
              setAdminTyping(true);
            }
          } else if (message.type === "STOP_TYPING") {
            if (message.sender === "Admin") {
              setAdminTyping(false);
            }
          } else {
            const newMsg = {
              ...message,
              seen: false
            };
            setMessages(prevMessages => [...prevMessages, newMsg]);
            setSeen(false);
          }
        } catch (error) {
          console.log('Received non-JSON message: ', event.data);
          setMessages(prevMessages => [...prevMessages, { text: event.data, seen: false }]);
        }
      };

      ws.current.onerror = (error) => {
        console.log('WebSocket Error:', error);
      };

      ws.current.onclose = (event) => {
        console.log('WebSocket closed. Reconnecting...', event);
        setTimeout(connectWebSocket, 5000);
      };
    };

    connectWebSocket();

    return () => {
      if (ws.current) {
        ws.current.close();
      }
    };
  }, []);

  const sendSeenMessage = useCallback(() => {
    if (messages.length > 0) {
      const seenMessage = {
        type: "SEEN",
        sender: sessionStorage.getItem("LoginId"),
        receiver: "Admin"
      };
      ws.current.send(JSON.stringify(seenMessage));
      console.log("SEEN message sent from user to admin:", seenMessage);
    }
  }, [messages]);

  useEffect(() => {
    const chatContainer = document.getElementById('chat-container');
    if (chatContainer) {
      chatContainer.addEventListener('mouseenter', sendSeenMessage);
    }

    return () => {
      if (chatContainer) {
        chatContainer.removeEventListener('mouseenter', sendSeenMessage);
      }
    };
  }, [sendSeenMessage]);

  const handleTyping = () => {
    if (!typing) {
      setTyping(true);
      ws.current.send(JSON.stringify({
        type: "TYPING",
        sender: sessionStorage.getItem("LoginId"),
        receiver: "Admin"
      }));
    }

    if (typingTimeoutRef.current) {
      clearTimeout(typingTimeoutRef.current);
    }

    typingTimeoutRef.current = setTimeout(() => {
      setTyping(false);
      ws.current.send(JSON.stringify({
        type: "STOP_TYPING",
        sender: sessionStorage.getItem("LoginId"),
        receiver: "Admin"
      }));
    }, 3000);
  };

  const sendMessage = (e) => {
    e.preventDefault();
    if (newMessage.trim() !== "" && ws.current && ws.current.readyState === WebSocket.OPEN) {
      const messageToSend = {
        sender: sessionStorage.getItem("LoginId"),
        content: newMessage,
        receiver: "Admin",
        seen: false
      };
      ws.current.send(JSON.stringify(messageToSend));
      setMessages(messages => [...messages, { ...messageToSend, sender: 'You' }]);
      setNewMessage("");
      setSeen(false);
      console.log("Message sent from user to admin:", messageToSend);
    }
  };

  return (
    <Container>
      <h2>Chat with Admin</h2>
      <div
        id="chat-container"
        className="messages"
        style={{ height: '400px', overflowY: 'auto', marginBottom: '20px' }}
      >
        {messages.map((msg, idx) => (
          <div key={idx} className={msg.sender === "You" ? "my-message" : "admin-message"}>
            {msg.sender}: {msg.content}
          </div>
        ))}
        {seen && <div className="seen-message">Admin has seen your messages</div>}
        {adminTyping && <div className="typing-message">Admin is typing...</div>}
      </div>
      <Form onSubmit={sendMessage}>
        <Form.Group className="mb-3">
          <Form.Control
            type="text"
            placeholder="Type a message..."
            value={newMessage}
            onChange={e => setNewMessage(e.target.value)}
            onKeyDown={handleTyping}
          />
        </Form.Group>
        <Button variant="primary" type="submit">Send</Button>
      </Form>
    </Container>
  );
}

export default UserChatPage;
