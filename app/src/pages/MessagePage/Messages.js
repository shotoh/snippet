import React, { useState, useEffect } from "react";
import NavBar from "../../components/MainPage/NavBar";
import { InputGroup, Form } from "react-bootstrap";

export default function MessagesPage() {
  // Friends of user
  const [friends, setFriends] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  const [selectedMessage, setSelectedMessage] = useState(null);

  // Helper function to parse JWT token
  const parseJwt = (token) => {
    try {
      const base64Url = token.split(".")[1];
      const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
      return JSON.parse(window.atob(base64));
    } catch (error) {
      return null;
    }
  };

  // Fetch friends list on component mount
  useEffect(() => {
    // Retrieve list of friends associated with user
    async function fetchFriends() {
      const token = localStorage.getItem("authToken");
      if (!token) {
        setError("User is not authenticated");
        setLoading(false);
        return;
      }

      // Get user ID from token
      const userId = parseJwt(token).sub;
      console.log("[TOKEN]:", token);
      console.log("[USER ID]:", userId);

      try {
        const response = await fetch(`/api/friends?from=${userId}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        const result = await response.json();

        if (response.ok && result.status === "success") {
          // Parse specifically the friends from response
          const friendsList = result.data.filter(
            (friend) => friend.status === "FRIEND" // Status should be "FRIEND", not "PENDING"
          );

          // Extract just the friend's id, name, and picture
          const friendData = friendsList.map((friend) => ({
            id: friend.to.id,
            displayName: friend.to.displayName,
            profilePicture: friend.to.profilePicture,
          }));

          setFriends(friendData);
          console.log("[FRIENDS]:", friendData);
        } else {
          setError("Error loading friends");
        }
      } catch (err) {
        console.error("Error loading friends:", err);
        setError("Error loading friends");
      } finally {
        setLoading(false);
      }
    }

    fetchFriends();
    console.log(friends);
  }, []);

  return (
    <div>
      {/* Navbar */}
      <NavBar />

      {/* Message section */}
      <div className="d-flex" style={{ height: "calc(100vh - 70px)" }}>
        {/* Left Sidebar */}
        <div className="col-3 border-end p-3" style={{ overflowY: "scroll" }}>
          <h4>Messages</h4>
          {/* Search Input */}
          <InputGroup className="mb-3">
            <Form.Control
              placeholder="Search Messages"
              aria-label="Search Messages"
            />
            <InputGroup.Text>
              <i className="bi bi-search"></i>
            </InputGroup.Text>
          </InputGroup>

          {/* Message Placeholder */}
          <ul className="list-unstyled">
            <p>
              No messages available. Message data will appear here once fetched.
            </p>
          </ul>
        </div>

        {/* Right Sidebar */}
        <div className="col-9 d-flex justify-content-center align-items-center bg-blue-300">
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
