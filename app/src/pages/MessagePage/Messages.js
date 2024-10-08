import React, { useState } from 'react';
import NavBar from "../../components/MainPage/NavBar";
import {InputGroup, Form } from 'react-bootstrap';


export default function MessagesPage() {
    const [selectedMessage, setSelectedMessage] = useState(null);

  return (
    <div>
      {/* Navbar */}
      <NavBar />

      {/* Message section */}
      <div className="d-flex" style={{ height: 'calc(100vh - 70px)' }}>
        {/* Left Sidebar */}
        <div className="col-3 border-end p-3" style={{ overflowY: 'scroll' }}>
          <h4>Messages</h4>
          {/* Search Input */}
          <InputGroup className="mb-3">
            <Form.Control placeholder="Search Messages" aria-label="Search Messages" />
            <InputGroup.Text><i className="bi bi-search"></i></InputGroup.Text>
          </InputGroup>

          {/* Message Placeholder */}
          <ul className="list-unstyled">
            <p>No messages available. Message data will appear here once fetched.</p>
          </ul>
        </div>

        {/* Right Sidebar */}
        <div className="col-9 d-flex justify-content-center align-items-center">
          {selectedMessage ? (
            <div>
              {/* display content selected message */}
              <h4>{selectedMessage.username}</h4>
              <p>{selectedMessage.text}</p>
            </div>
          ) : (
            <p>Select a message to view its content.</p> //placeholder for selected DM
          )}
        </div>
      </div>
    </div>
  );
}