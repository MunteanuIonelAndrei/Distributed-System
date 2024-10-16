import React, { useState } from "react";
import { Button, Modal } from "react-bootstrap";

function MyPopupFail() {
  const [show, setShow] = useState(true); // Set the default state to true

  const handleClose = () => setShow(false);

  return (
    <div>
      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>LogIn State</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <p> LogIn Fail </p>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
}

export { MyPopupFail };
