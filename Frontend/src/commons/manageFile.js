import React, { useState } from "react";
import { Button,Table, Container, Form } from "react-bootstrap";
import BackgroundImg from '../commons/images/112.jpg';
import * as Api_User from "../person/api/person-api";
import { useHistory } from 'react-router-dom';

const backgroundStyle = {
  backgroundPosition: 'center',
  backgroundSize: 'cover',
  backgroundRepeat: 'no-repeat',
  width: "100%",
  height: "100vh",
  backgroundImage: `url(${BackgroundImg})`
};

function ManagePerson(props) {
  const [persons, setPersons] = useState([]);
  const [devices, setDevices] = useState([]);
  const [userAction, setUserAction] = useState(""); // "createUser", "updateUser", or "deleteUser"
  const [deviceAction, setDeviceAction] = useState(""); // "createDevice", "updateDevice", or "deleteDevice"
  const history = useHistory();

  const [formDataUser, setFormDataUser] = useState({
    id: "",
    name: "",
    //role: "",
    password: "",
  });

  const goToChatPage = () => {
    history.push('/chat'); // Navigate to the Chat Page
  };

  const [formDataDevice, setFormDataDevice] = useState({
    id: "",
    maxHours: "",
    description: "",
    address: "",
  });

  const fetchPersons = () => {
    Api_User.getPersons((response, status, error) => {
      if (status === 200) {
        setPersons(response);
      } else {
        console.error("Failed to fetch persons:", error);
      }
    });
  };
  
  const fetchDevices = () => {
    Api_User.getAllDevices((response, status, error) => {
      if (status === 200) {
        setDevices(response);
      } else {
        console.error("Failed to fetch devices:", error);
      }
    });
  };
  

  const handleInputChangeUser = (e) => {
    const { name, value } = e.target;
    setFormDataUser({ ...formDataUser, [name]: value });
  };

  const handleInputChangeDevice = (e) => {
    const { name, value } = e.target;
    setFormDataDevice({ ...formDataDevice, [name]: value });
  };

  const handleUserAction = (selectedAction) => {
    setUserAction(selectedAction);
    // Reset the device action to clear the device fields
    setDeviceAction("");
  };

  const handleDeviceAction = (selectedAction) => {
    setDeviceAction(selectedAction);
    // Reset the user action to clear the user fields
    setUserAction("");
  };

  const handleCreateUpdateDeleteUser = () => {
    if (userAction === "createUser") {
      // Implement user creation logic here
      const newUser = {
        name: formDataUser.name,
       // role: formDataUser.role,
        password: formDataUser.password,
      };
      Api_User.postPerson(newUser, (response, status, error) => {
        if (status === 201) {
          console.log("User created successfully.");
        } else {
          console.error("Create error:", error);
        }
      });
    } else if (userAction === "readUser"){
      fetchPersons();

    }else if (userAction === "updateUser") {
      // Implement user update logic here
      const updatedUser = {
        id: formDataUser.id,
        name: formDataUser.name,
        password: formDataUser.password,
      };
      Api_User.updatePerson(updatedUser.id, updatedUser.name, updatedUser.password, (response, status, error) => {
        if (status === 200) {
          console.log("User updated successfully.");
        } else {
          console.error("Update error:", error);
        }
      });
    } else if (userAction === "deleteUser") {
      // Implement user deletion logic here
      const userId = formDataUser.id;
      Api_User.deletePerson(userId, (response, status, error) => {
        if (status === 204) {
          console.log("User deleted successfully.");
        } else {
          console.error("Delete error:", error);
        }
      });
    }
  };

  const handleCreateUpdateDeleteDevice = () => {
    if (deviceAction === "createDevice") {
        const deviceId = formDataDevice.id;
      // Implement device creation logic here
      const newDevice = {
       
        name: formDataDevice.description,
        address: formDataDevice.address,
        maxHours: formDataDevice.maxHours,
      };
      Api_User.postDevice(deviceId,newDevice, (response, status, error) => {
        if (status === 201) {
          console.log("Device created successfully.");
        } else {
          console.error("Create error:", error);
        }
      });
    } else if (deviceAction === "readDevice"){
      fetchDevices();

    }
    else if (deviceAction === "updateDevice") {
      // Implement device update logic here
      const updatedDevice = {
        id: formDataDevice.id,
        maxHours: formDataDevice.maxHours,
        description: formDataDevice.description,
        address: formDataDevice.address,
      };
      Api_User.updateDevice(updatedDevice.id, updatedDevice.description, updatedDevice.address,updatedDevice.maxHours, (response, status, error) => {
        if (status === 200) {
          console.log("Device updated successfully.");
        } else {
          console.error("Update error:", error);
        }
      });

    } else if (deviceAction === "deleteDevice") {
      // Implement device deletion logic here
      const deviceId = formDataDevice.id;
      Api_User.deleteDevice(deviceId, (response, status, error) => {
        if (status === 204) {
          console.log("Device deleted successfully.");
        } else {
          console.error("Delete error:", error);
        }
      });
    }
}

  return (
    <Container fluid style={backgroundStyle}>
      <div className="d-flex justify-content-center mt-4">
        <Button className="mx-2" variant="primary" onClick={() => handleUserAction("createUser")}>
          Create User
        </Button>
        <Button className="mx-2" variant="primary" onClick={() => handleUserAction("readUser")}>
          Read User
        </Button>
        <Button className="mx-2" variant="primary" onClick={() => handleUserAction("updateUser")}>
          Update User
        </Button>
        <Button className="mx-2" variant="primary" onClick={() => handleUserAction("deleteUser")}>
          Delete User
        </Button>
        <Button className="mx-2" variant="primary" onClick={goToChatPage}>
          Open Chat
        </Button>
       
      </div>
      <div className="d-flex justify-content-center mt-4">
        <Button className="mx-2" variant="primary" onClick={() => handleDeviceAction("createDevice")}>
          Create Device
        </Button>
        <Button className="mx-2" variant="primary" onClick={() => handleDeviceAction("readDevice")}>
          Read Device
        </Button>
        <Button className="mx-2" variant="primary" onClick={() => handleDeviceAction("updateDevice")}>
          Update Device
        </Button>
        <Button className="mx-2" variant="primary" onClick={() => handleDeviceAction("deleteDevice")}>
          Delete Device
        </Button>
      </div>
      {userAction && (
        <Form className="mt-4">
          {/* User form inputs */}
          {userAction === "createUser" && (
            <>
              <Form.Group>
                <Form.Control
                  name="name"
                  type="text"
                  placeholder="Name"
                  size="lg"
                  value={formDataUser.name}
                  onChange={handleInputChangeUser}
                />
              </Form.Group>
              {/* <Form.Group>
                <Form.Control
                  name="role"
                  type="text"
                  placeholder="Role"
                  size="lg"
                  value={formDataUser.role}
                  onChange={handleInputChangeUser}
                />
              </Form.Group> */}
              <Form.Group>
                <Form.Control
                  name="password"
                  type="password"
                  placeholder="Password"
                  size="lg"
                  value={formDataUser.password}
                  onChange={handleInputChangeUser}
                />
              </Form.Group>
            </>
          )}
          {userAction === "readUser" && (
  <Table striped bordered hover>
    <thead>
      <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Role</th>
        <th>Password</th>
        {/* Add more headers if needed */}
      </tr>
    </thead>
    <tbody>
      {persons.map((person, index) => (
        <tr key={index}>
          <td>{person.id}</td>
          <td>{person.password}</td>
          <td>{person.name}</td>
          <td>{person.role}</td>
          {/* Render more data if needed */}
        </tr>
      ))}
    </tbody>
  </Table>
)}
          {userAction === "updateUser" && (
            <>
              <Form.Group>
                <Form.Control
                  name="id"
                  type="text"
                  placeholder="ID"
                  size="lg"
                  value={formDataUser.id}
                  onChange={handleInputChangeUser}
                />
              </Form.Group>
              <Form.Group>
                <Form.Control
                  name="name"
                  type="text"
                  placeholder="Name"
                  size="lg"
                  value={formDataUser.name}
                  onChange={handleInputChangeUser}
                />
              </Form.Group>
              <Form.Group>
                <Form.Control
                  name="password"
                  type="password"
                  placeholder="Password"
                  size="lg"
                  value={formDataUser.password}
                  onChange={handleInputChangeUser}
                />
              </Form.Group>
            </>
          )}
          {userAction === "deleteUser" && (
            <Form.Group>
              <Form.Control
                name="id"
                type="text"
                placeholder="ID"
                size="lg"
                value={formDataUser.id}
                onChange={handleInputChangeUser}
              />
            </Form.Group>
          )}
          <div className="d-flex justify-content-center mt-4">
            <Button size="lg" variant="primary" onClick={handleCreateUpdateDeleteUser}>
              {userAction === "createUser" && "Create User"}
              {userAction === "updateUser" && "Update User"}
              {userAction === "deleteUser" && "Delete User"}
            </Button>
          </div>
        </Form>
      )}
      {deviceAction && (
        <Form className="mt-4">
          {/* Device form inputs */}
          {deviceAction === "createDevice" && (
            <>
              <Form.Group>
                <Form.Control
                  name="id"
                  type="text"
                  placeholder="ID"
                  size="lg"
                  value={formDataDevice.id}
                  onChange={handleInputChangeDevice}
                />
              </Form.Group>
              <Form.Group>
                <Form.Control
                  name="maxHours"
                  type="text"
                  placeholder="Max Hours"
                  value={formDataDevice.maxHours}
                  onChange={handleInputChangeDevice}
                />
              </Form.Group>
              <Form.Group>
                <Form.Control
                  name="description"
                  type="text"
                  placeholder="Description"
                  value={formDataDevice.description}
                  onChange={handleInputChangeDevice}
                />
              </Form.Group>
              <Form.Group>
                <Form.Control
                  name="address"
                  type="text"
                  placeholder="Address"
                  value={formDataDevice.address}
                  onChange={handleInputChangeDevice}
                />
              </Form.Group>
            </>
          )}
          {deviceAction === "readDevice" && (
  <Table striped bordered hover>
    <thead>
      <tr>
        <th>ID</th>
        <th>Max Hours</th>
        <th>Description</th>
        <th>Address</th>

        {/* Add more headers if needed */}
      </tr>
    </thead>
    <tbody>
      {devices.map((device, index) => (
        <tr key={index}>
          <td>{device.id}</td>
          <td>{device.maxHours}</td>
          <td>{device.description}</td>
          <td>{device.address}</td>

          {/* Render more data if needed */}
        </tr>
      ))}
    </tbody>
  </Table>
)}
          {deviceAction === "updateDevice" && (
            <>
              <Form.Group>
                <Form.Control
                  name="id"
                  type="text"
                  placeholder="ID"
                  size="lg"
                  value={formDataDevice.id}
                  onChange={handleInputChangeDevice}
                />
              </Form.Group>
              <Form.Group>
                <Form.Control
                  name="maxHours"
                  type="text"
                  placeholder="Max Hours"
                  value={formDataDevice.maxHours}
                  onChange={handleInputChangeDevice}
                />
              </Form.Group>
              <Form.Group>
                <Form.Control
                  name="description"
                  type="text"
                  placeholder="Description"
                  value={formDataDevice.description}
                  onChange={handleInputChangeDevice}
                />
              </Form.Group>
              <Form.Group>
                <Form.Control
                  name="address"
                  type="text"
                  placeholder="Address"
                  value={formDataDevice.address}
                  onChange={handleInputChangeDevice}
                />
              </Form.Group>
            </>
          )}
          {deviceAction === "deleteDevice" && (
            <Form.Group>
              <Form.Control
                name="id"
                type="text"
                placeholder="ID"
                size="lg"
                value={formDataDevice.id}
                onChange={handleInputChangeDevice}
              />
            </Form.Group>
          )}
          <div className="d-flex justify-content-center mt-4">
            <Button size="lg" variant="primary" onClick={handleCreateUpdateDeleteDevice}>
              {deviceAction === "createDevice" && "Create Device"}
              {deviceAction === "updateDevice" && "Update Device"}
              {deviceAction === "deleteDevice" && "Delete Device"}
            </Button>
          </div>
        </Form>
      )}
    </Container>
  );
}

export { ManagePerson };