import React, { useState } from "react";
import { Form } from "react-bootstrap";

export default function MessageBar({ selectedMessage }) {
  const [message, setMessage] = useState("");

  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      e.preventDefault();
      if (message.trim()) {
        // Handle message submission here
        console.log("Message sent:", message);
        setMessage("");
      }
    }
  };

  return (
    <div className="px-3 pb-3 flex items-center">
      {/* Message Input Field */}
      <Form.Control
        type="text"
        placeholder={`Message ${selectedMessage.displayName}...`}
        value={message}
        onChange={(e) => setMessage(e.target.value)}
        onKeyDown={handleKeyDown}
        className="flex-grow mr-3"
      />
    </div>
  );
}
