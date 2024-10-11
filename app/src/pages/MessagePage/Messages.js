import React, { useState, useEffect } from "react";
import NavBar from "../../components/MainPage/NavBar";
import { InputGroup, Form } from "react-bootstrap";

import MessageHeader from "../../components/MessagePage/MessageHeader";
import MessageBody from "../../components/MessagePage/MessageBody";
import MessageBar from "../../components/MessagePage/MessageBar";

export default function MessagesPage() {
  // Friends of user
  const [friends, setFriends] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  const [userId, setUserId] = useState(null);

  const [selectedFriend, setSelectedFriend] = useState({
    id: 11,
    username: "someguy",
    displayName: "Some Guy",
    profilePicture: require("../../images/jackblack.jpg"),
  });
  const [messages, setMessages] = useState([]);
  const authToken = localStorage.getItem("authToken");

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

  useEffect(() => {
    // Retrieve list of friends associated with user
    async function fetchFriends() {
      const token = authToken;
      if (!token) {
        setError("User is not authenticated");
        setLoading(false);
        return;
      }

      // Get user ID from token and set it in state
      const userIdFromToken = parseInt(parseJwt(token).sub);
      setUserId(userIdFromToken);

      console.log("[TOKEN]:", token);
      console.log("[USER ID]:", userIdFromToken);

      try {
        const response = await fetch(`/api/friends?from=${userIdFromToken}`, {
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
  }, []);

  // Load messages for selected friend
  const loadMessages = async () => {
    if (!userId) {
      console.error("User ID is not available.");
      return;
    }

    const token = authToken;
    if (!token) {
      setError("Token has expired. Please log in again.");
      return;
    }

    try {
      const response = await fetch(
        `/api/messages?from=${userId}&to=${selectedFriend.id}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      const result = await response.json();

      if (response.ok && result.status === "success") {
        // Set messages to state
        setMessages(result.data);
        console.log("[MESSAGES]:", result.data);
      } else {
        setError("Error loading messages");
      }
    } catch (err) {
      console.error("Error loading messages:", err);
      setError("Error loading messages");
    }
  };

  // Handler for new messages sent by the user
  const handleNewMessage = (newMessage) => {
    setMessages((prevMessages) => [...prevMessages, newMessage]);
  };

  return (
    <div>
      {/* Navbar */}
      <NavBar />
      <div
        className="container mx-auto mt-4 mb-4 border-2 p-0 border-secondaryLight"
        style={{ height: "calc(100vh - 100px)" }}
      >
        {/* Message section */}
        <div className="d-flex h-100">
          {/* Left Sidebar */}
          <div
            className="col-3 border-r-2 border-secondaryLight pt-4 px-3"
            style={{ overflowY: "scroll" }}
          >
            <h2 className="font-montserrat font-bold">Messages</h2>
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
                No messages available. Message data will appear here once
                fetched.
              </p>
            </ul>
          </div>

          {/* Right Sidebar */}
          <div className="col-9 d-flex flex-column h-100 ">
            {selectedFriend ? (
              <>
                <MessageHeader selectedFriend={selectedFriend} />
                <MessageBody messages={messages} userId={userId} />
                <MessageBar
                  selectedFriend={selectedFriend}
                  userId={userId}
                  authToken={authToken}
                  onNewMessage={handleNewMessage}
                />
                <button onClick={loadMessages}>
                  <i className="bi bi-arrow-left"></i> Back to Messages
                </button>
              </>
            ) : (
              <p>Select a message to view its content.</p> //placeholder for selected DM
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
