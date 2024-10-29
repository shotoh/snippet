import React, { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import Nav from "react-bootstrap/Nav";
import { NavLink } from "react-router-dom";
//TO-DO: Add save change functionality once backend password change is finished
//TO-DO: Abstract code wherever possible (changing password, )


export default function SettingsModal({ show, handleClose }) {
    const [currentPassword, setCurrentPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    const handleLogout = () => {
        localStorage.removeItem("authToken");
        window.location.href = "/login";
      }

    const handlePasswordChange = () => {
        if (newPassword !== confirmPassword) {
            alert("New passwords do not match");
            return;
        }
        console.log("Saving new password");
        handleClose();
    }

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
          <NavLink className="yes-underline text-inherit" to="/home/contact"> {/* Placeholder, waiting for Help Document to be made */}
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
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
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
        <Button variant="secondary" onClick={handlePasswordChange}> {/* Handle save changes */}
          Save and Close
        </Button>
        <Button variant="primary" onClick={handleLogout}>
          Logout
        </Button>
      </Modal.Footer>
    </Modal>
  );
}