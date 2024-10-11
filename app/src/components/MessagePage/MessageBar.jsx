import React, { useState, useRef, useEffect } from "react";
import { Form } from "react-bootstrap";

function MessageBar({ selectedFriend, userId, authToken, onNewMessage }) {
  const [message, setMessage] = useState("");
  const [isSending, setIsSending] = useState(false);
  const [error, setError] = useState(null);

  const inputRef = useRef(null);

  // Focus the input field after sending the message
  useEffect(() => {
    if (!isSending && inputRef.current) {
      inputRef.current.focus();
    }
  }, [isSending]);

  const handleKeyDown = async (e) => {
    if (e.key === "Enter") {
      e.preventDefault();

      const trimmedMessage = message.trim();
      if (trimmedMessage) {
        setIsSending(true);
        setError(null);
        try {
          // Send the message to the backend
          const response = await fetch("/api/messages", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${authToken}`,
            },
            body: JSON.stringify({
              fromId: parseInt(userId),
              toId: selectedFriend.id,
              content: trimmedMessage,
            }),
          });

          const result = await response.json();

          if (response.ok && result.status === "success") {
            // Clear the input field
            setMessage("");

            // Notify parent component of the new message
            if (onNewMessage) {
              onNewMessage(result.data);
            }
          } else {
            console.error("Failed to send message:", result.message);
            setError("Failed to send message. Please try again.");
          }
        } catch (error) {
          console.error("Error sending message:", error);
          setError("An error occurred while sending the message.");
        } finally {
          setIsSending(false);
        }
      }
    }
  };

  return (
    <div className="px-3 pb-3 flex items-center flex-col">
      <Form.Control
        ref={inputRef}
        type="text"
        placeholder={`Message ${selectedFriend.displayName}...`}
        value={message}
        onChange={(e) => setMessage(e.target.value)}
        onKeyDown={handleKeyDown}
        className="flex-grow mr-3"
        disabled={isSending}
      />
      {error && <div className="text-danger mt-2">{error}</div>}
    </div>
  );
}

export default React.memo(MessageBar);
