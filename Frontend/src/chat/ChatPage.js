import React, { useState, useEffect, useCallback, useRef } from 'react';
import { Container, Row, Col, ListGroup, Form, Button } from 'react-bootstrap';
import * as Api_User from "../person/api/person-api";

function ChatPage() {
    const [users, setUsers] = useState([]);
    const [selectedUser, setSelectedUser] = useState(null);
    const [messages, setMessages] = useState({});
    const [newMessage, setNewMessage] = useState("");
    const [seen, setSeen] = useState({});
    const [userTyping, setUserTyping] = useState(false);
    const [typing, setTyping] = useState(false);
    const ws = useRef(null);
    const typingTimeoutRef = useRef(null);
    const userTypingTimeoutRef = useRef(null);

    const selectUser = (user) => {
        setSelectedUser(user);
        const seenMessage = {
            type: "SEEN",
            sender: "Admin",
            receiver: user.id
        };
        ws.current.send(JSON.stringify(seenMessage));
        console.log("SEEN message sent from admin to user:", seenMessage);
    };

    useEffect(() => {
        // Fetch users
        Api_User.getPersons((response, status, error) => {
            if (status === 200) {
                setUsers(response);
            } else {
                console.error("Failed to fetch users:", error);
            }
        });

        // Setup WebSocket connection
        const loginId = sessionStorage.getItem("LoginId");
        const role = sessionStorage.getItem("Role");
       // ws.current = new WebSocket(`ws://localhost:8083/websocket?LoginId=${loginId}&Role=${role}`);
        ws.current = new WebSocket(`ws://localhost:8074/websocket?LoginId=${loginId}&Role=${role}`);

        ws.current.onmessage = (event) => {
            try {
                const msgData = JSON.parse(event.data);
                console.log("Message received: ", msgData);

                if (msgData.type === "SEEN") {
                    console.log(`Messages seen by ${msgData.sender}`);
                    setSeen(prevSeen => ({
                        ...prevSeen,
                        [msgData.sender]: true
                    }));
                } else if (msgData.type === "TYPING") {
                    if (selectedUser && msgData.sender === selectedUser.id) {
                        console.log("User is typing...");
                        setUserTyping(true);
                        if (userTypingTimeoutRef.current) {
                            clearTimeout(userTypingTimeoutRef.current);
                        }
                        userTypingTimeoutRef.current = setTimeout(() => {
                            setUserTyping(false);
                        }, 3000);
                    }
                } else if (msgData.type === "STOP_TYPING") {
                    if (selectedUser && msgData.sender === selectedUser.id) {
                        console.log("User stopped typing...");
                        setUserTyping(false);
                    }
                } else {
                    const formattedMessage = {
                        id: msgData.id ? msgData.id.toString() : Date.now().toString(),
                        sender: msgData.sender || 'System',
                        content: msgData.content || 'No content received',
                        receiver: msgData.receiver || 'Unknown',
                        seen: false
                    };

                    setMessages(prevMessages => ({
                        ...prevMessages,
                        [formattedMessage.sender]: [...(prevMessages[formattedMessage.sender] || []), formattedMessage]
                    }));
                }
            } catch (error) {
                console.error('Error parsing message:', error);
            }
        };

        ws.current.onopen = () => console.log('WebSocket Connected');
        ws.current.onerror = error => console.log('WebSocket Error:', error);
        ws.current.onclose = () => {
            console.log('WebSocket Disconnected. Reconnecting...');
            setTimeout(() => {
                ws.current = new WebSocket(`ws://localhost:8074/websocket?LoginId=${loginId}&Role=${role}`);
            }, 5000);
        };

        return () => {
            if (ws.current) {
                ws.current.close();
            }
        };
    }, [selectedUser]);

    const sendSeenMessage = useCallback(() => {
        if (selectedUser) {
            const seenMessage = {
                type: "SEEN",
                sender: "Admin",
                receiver: selectedUser.id
            };
            ws.current.send(JSON.stringify(seenMessage));
            console.log("SEEN message sent from admin to user:", seenMessage);
        }
    }, [selectedUser]);

    useEffect(() => {
        const chatContainer = document.getElementById('chat-container-admin');
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
        if (!typing && selectedUser) {
            setTyping(true);
            ws.current.send(JSON.stringify({
                type: "TYPING",
                sender: "Admin",
                receiver: selectedUser.id
            }));
        }

        if (typingTimeoutRef.current) {
            clearTimeout(typingTimeoutRef.current);
        }

        typingTimeoutRef.current = setTimeout(() => {
            setTyping(false);
            if (selectedUser) {
                ws.current.send(JSON.stringify({
                    type: "STOP_TYPING",
                    sender: "Admin",
                    receiver: selectedUser.id
                }));
            }
        }, 3000);
    };

    const sendMessage = (e) => {
        e.preventDefault();
        if (ws.current && newMessage.trim() !== "" && selectedUser) {
            const messageToSend = {
                sender: 'Admin',
                content: newMessage,
                receiver: selectedUser.id,
                seen: false
            };

            setMessages(prevMessages => ({
                ...prevMessages,
                [selectedUser.id]: [...(prevMessages[selectedUser.id] || []), { ...messageToSend, sender: 'You' }]
            }));

            ws.current.send(JSON.stringify(messageToSend));
            setNewMessage("");
            setSeen(prevSeen => ({
                ...prevSeen,
                [selectedUser.id]: false
            }));
            console.log("Message sent from admin to user:", messageToSend);
        }
    };

    return (
        <Container fluid>
            <Row>
                <Col xs={4} style={{ maxHeight: '100vh', overflowY: 'auto' }}>
                    <ListGroup>
                        {users.map(user => (
                            <ListGroup.Item key={user.id} action onClick={() => selectUser(user)}>
                                {user.name} ({user.id})
                            </ListGroup.Item>
                        ))}
                    </ListGroup>
                </Col>
                <Col xs={8} style={{ maxHeight: '100vh', overflowY: 'auto' }}>
                    {selectedUser && (
                        <>
                            <h4>Chat with {selectedUser.name}</h4>
                            <div
                                id="chat-container-admin"
                                className="messages"
                                style={{ height: '400px', overflowY: 'auto', marginBottom: '20px' }}
                            >
                                {(messages[selectedUser.id] || []).map((msg, idx) => (
                                    <div key={idx} className={msg.sender === "You" ? "my-message" : "admin-message"}>
                                        {msg.sender}: {msg.content}
                                    </div>
                                ))}
                                {seen[selectedUser.id] && <div className="seen-message">User has seen your messages</div>}
                                {userTyping && <div className="typing-message">User is typing...</div>}
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
                        </>
                    )}
                </Col>
            </Row>
        </Container>
    );
}

export default ChatPage;
