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
      console.error("Passwords do not match");
      alert("New passwords do not match");
      return;
    }
  
    const token = localStorage.getItem("authToken");
    if (!token) {
      console.error("No token found");
      alert("You are not logged in. Please log in again.");
      return;
    }
  
    try {
      await changeUserPassword(currentPassword, newPass, token);
      alert("Password changed successfully");
      handleClose();
    } catch (error) {
      console.error("Error changing password: ", error.message);
      alert(error.message || "Failed to change password. Please try again.");
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
          <NavLink className="yes-underline text-inherit" 
            to="#"
            onClick={() => window.open("https://docs.google.com/document/d/14QQrpovUKXw_Z20qzw2-hpLttxHIBCXxvXJrOfukfvU/edit?tab=t.0#heading=h.ke3rj2c3skhy", "_blank")} //Opens new tab for help link
            >
            Click to recieve Help
          </NavLink>
        </Nav.Link>
        <hr /> {/* line to divide popup content */}
        <h5 className="mb-3">Change Password</h5>
        <Form>
          <Form.Group>
            <Form.Label htmlFor="currentPassword"> Enter Current Password </Form.Label>
            <Form.Control
              id="currentPassword"
              type="password"
              placeholder="Current Password"
              value={currentPassword}
              onChange={(e) => setCurrentPassword(e.target.value)}
              className="mb-3"
            />
            <Form.Label htmlFor="newPassword"> Enter New Password </Form.Label>
            <Form.Control
              id="newPassword"
              type="password"
              placeholder="New Password"
              value={newPass}
              onChange={(e) => setNewPass(e.target.value)}
              className="mb-3"
            />
            <Form.Label htmlFor="confirmPassword"> Confirm New Password</Form.Label>
            <Form.Control
              id="confirmPassword"
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
