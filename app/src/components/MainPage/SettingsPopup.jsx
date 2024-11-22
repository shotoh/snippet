import React, { useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";
import Nav from "react-bootstrap/Nav";
import { NavLink } from "react-router-dom";
import { changeUserPassword } from "../../api/ProfileAPI";

export default function SettingsModal({ show, handleClose }) {
  const [currentPassword, setCurrentPassword] = useState("");
  const [newPass, setNewPass] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const handleLogout = () => {
    localStorage.removeItem("authToken");
    window.location.href = "/login";
  };

  const handlePasswordChange = async () => {
    if (newPass !== confirmPassword) {
      alert("New passwords do not match");
      return;
    }

    const token = localStorage.getItem("authToken");
    try {
      await changeUserPassword(currentPassword, newPass, token);
      alert("Password changed successfully");
      handleClose();
    } catch (error) {
      console.error("Error changing password: ", error);
      alert(error.message || "Error occurred while changing password");
    }
  };

  return (
    <Modal show={show} onHide={handleClose} centered>
      <Modal.Header closeButton>
        <Modal.Title>Settings</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Nav.Link>
          <NavLink className="yes-underline text-inherit" to="/home/about">
            Click to see About page
          </NavLink>
        </Nav.Link>
        <Nav.Link>
          <NavLink className="yes-underline text-inherit" to="/home/contact">
            {" "}
            {/* Placeholder, waiting for Help Document to be made */}
            Click to recieve Help
          </NavLink>
        </Nav.Link>
        <hr /> {/* line to divide popup content */}
        <h5 className="mb-3">Change Password</h5>
        <Form>
          <Form.Group controlId="passwordChange">
            <Form.Label>Enter Current Password</Form.Label>
            <Form.Control
              type="password"
              placeholder="Current Password"
              value={currentPassword}
              onChange={(e) => setCurrentPassword(e.target.value)}
              className="mb-3"
            />
            <Form.Label>Enter New Password </Form.Label>
            <Form.Control
              type="password"
              placeholder="New Password"
              value={newPass}
              onChange={(e) => setNewPass(e.target.value)}
              className="mb-3"
            />
            <Form.Label> Confirm New Password</Form.Label>
            <Form.Control
              type="password"
              placeholder="Confirm New Password"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              className="mb-3"
            />
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handlePasswordChange}>
          Save and Close
        </Button>
        <Button variant="primary" onClick={handleLogout}>
          Logout
        </Button>
      </Modal.Footer>
    </Modal>
  );
}
