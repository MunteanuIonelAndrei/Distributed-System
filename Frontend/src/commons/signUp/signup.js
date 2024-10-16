import React, { useState } from "react";
import { Button, Container, Form, Modal } from "react-bootstrap";
import BackgroundImg from '../images/112.jpg';
import * as Api_User from "../../person/api/person-api";

const backgroundStyle = {
  backgroundPosition: 'center',
  backgroundSize: 'cover',
  backgroundRepeat: 'no-repeat',
  width: "100%",
  height: "100vh",
  backgroundImage: `url(${BackgroundImg})`
};

function SignUp(props) {
  const [formData, setFormData] = useState({
    name: "",
    password: "",
  });

  const [personId, setPersonId] = useState(null);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSignUp = () => {
    const { name, password } = formData;

    const user = {
      name,
      password,
    };

    Api_User.postPerson(user, (result, status, error) => {
      if (status === 200 || status === 201) {
        console.log("Successfully inserted person with id: " + result);
      
        setPersonId(result); // Set personId to the returned ID
      } else {
        console.error("API Error:", error);
      }
    });
  };

  const handleClose = () => setPersonId(null);

  return (
    <Container fluid style={backgroundStyle}>
      <Form>
        <Form.Group>
          <Form.Control
            name="name"
            type="text"
            placeholder="Name"
            size="lg"
            value={formData.name}
            onChange={handleInputChange}
          />
        </Form.Group>

        <Form.Group>
          <Form.Control
            name="password"
            type="password"
            placeholder="Password"
            size="lg"
            value={formData.password}
            onChange={handleInputChange}
          />
        </Form.Group>
        <Button
          type="button"
          size="lg"
          variant="primary"
          onClick={handleSignUp}
        >
          SignUp
        </Button>
      </Form>

      {/* Display the person's ID in a modal */}
      <Modal show={personId !== null} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Person ID</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {personId !== null && <p>Person ID: {personId}</p>}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
}

export { SignUp };
