import React, { useState, useEffect, useRef } from 'react';
import { Container, Table,Button } from "react-bootstrap";
import BackgroundImg from '../images/112.jpg';
import { useLocation,useHistory  } from "react-router-dom";
import * as Api_User from "../../person/api/person-api";


const backgroundStyle = {
  backgroundPosition: 'center',
  backgroundSize: 'cover',
  backgroundRepeat: 'no-repeat',
  width: "100%",
  height: "100vh",
  backgroundImage: `url(${BackgroundImg})`
};

const tableStyle = {
  backgroundColor: 'white',
  borderRadius: '15px',
  boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.1)'
};


function UserPage() {
  const location = useLocation();
  const history = useHistory();
  const id = location.state.id;
  const [devices, setDevices] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);
  const [receivedData, setReceivedData] = useState([]);
  const [userIdReceived, setUserIdReceived] = useState(null); // Define userIdReceived in the state
  const [deviceData, setDeviceData] = useState({}); // Using an object to keep track of devices by their IDs


  const webSocketRef = useRef(null);
  const userId = sessionStorage.getItem("LoginId");
  const connectWebSocket = () => {

    const webSocket = new WebSocket('ws://localhost:8073/ws/devices'); // Replace with your WebSocket URL
    //const webSocket = new WebSocket('ws://localhost:8082/ws/devices'); // Replace with your WebSocket URL
    webSocket.onopen = () => {
      console.log('WebSocket Connected');
      webSocket.send(userId);
    };

    webSocket.onmessage = (message) => {
      console.log('Message from WebSocket:', message.data);
      const parsedData = JSON.parse(message.data);
      setReceivedData((prevData) => [...prevData, parsedData]);
      setUserIdReceived(parsedData.personId);
    

      if (parsedData.personId === userId) {
        setDeviceData(prevDeviceData => {
          const newData = { ...prevDeviceData };
          newData[parsedData.deviceId] = parsedData; // Update or add the device data
          return newData;
        });
        
        if (parsedData.meanExceedsMax) {
          alert(`Alert: Device ID ${parsedData.deviceId} mean value (${parsedData.meanValue.toFixed(2)}) exceeds max hours (${parsedData.maxHours}).`); // You can replace alert with a more sophisticated notification system
        }

        
      }

      // if (parsedData.meanValue > parsedData.maxHours) {
      //   window.alert(`Threshold exceeded for device ${parsedData.deviceId}: Mean value ${parsedData.meanValue.toFixed(4)} is greater than max hours ${parsedData.maxHours}.`);
      // }

     // setNotification(message.data); 
    };

    webSocket.onerror = (error) => {
      console.error('WebSocket Error:', error);
    };

    webSocket.onclose = () => {
      console.log('WebSocket Disconnected');
    };

    webSocketRef.current = webSocket;
  };

  console.log("userIdsessionStorage" + userId);
  console.log("user received "+ userIdReceived);
  useEffect(() => {

    fetchUserDevices();
    connectWebSocket();
    return () => {
      if (webSocketRef.current) {
        webSocketRef.current.close();
      }
    };
  }, []);

  const fetchUserDevices = () => {
    setIsLoading(true);
    Api_User.getDevices(id, (result, status, error) => {
      if (status === 200 || status === 201) {
        setDevices(result);
        setIsLoading(false);
      } else {
        setError("Failed to fetch devices. Please try again later.");
        setIsLoading(false);
      }
    });
  };
  const redirectToChat = () => {
    history.push('/user-chat');  // Ensure the route matches what's defined in your Router setup
  };
  if (isLoading) {
    return <div style={backgroundStyle}>Loading...</div>;
  }

  if (error) {
    return <div style={backgroundStyle}>Error: {error}</div>;
  }

  return (
    <div style={backgroundStyle}>
      <Container>
      <h2>User Devices</h2>
        <Button variant="primary" onClick={redirectToChat}>Chat with Admin</Button>
        <h2>User Devices</h2>
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Device ID</th>
              <th>Max Hours</th>
              <th>Description</th>
              <th>Address</th>
            </tr>
          </thead>
          <tbody>
            {devices.map((device, index) => (
              <tr key={index}>
                <td>{device.id}</td>
                <td>{device.maxHours}</td>
                <td>{device.description}</td>
                <td>{device.address}</td>
              </tr>
            ))}
          </tbody>
        </Table>
        <Table striped bordered hover style={tableStyle}>
          <thead>
            <tr>
              <th>Device ID</th>
              <th>Timestamp</th>
              <th>Mean Value</th>
              <th>Max Hours</th>
              <th>Values</th>
            </tr>
          </thead>
          <tbody>
            {Object.values(deviceData).map((data, index) => (
              <tr key={index}>
                <td>{data.deviceId}</td>
                <td>{new Date(data.timestamp).toLocaleString()}</td>
                <td>{data.meanValue.toFixed(2)}</td>
                <td>{data.maxHours}</td>
                <td>{data.doubleValues.join(', ')}</td>
              </tr>
            ))}
          </tbody>
        </Table>
      </Container>
    </div>
  );
}

export default UserPage;
