import React, { useState, useEffect } from "react";
import NavBar from "../../components/MainPage/NavBar";
import { InputGroup, Form } from "react-bootstrap";
import { UserCircleIcon } from "@heroicons/react/24/solid";

import MessageHeader from "../../components/MessagePage/MessageHeader";
import MessageBody from "../../components/MessagePage/MessageBody";
import MessageBar from "../../components/MessagePage/MessageBar";
import FriendCard from "../../components/MessagePage/FriendCard";

export default function MessagesPage() {
  // Friends of user
  const [friends, setFriends] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  const [userId, setUserId] = useState(null);

  const [selectedFriend, setSelectedFriend] = useState(null);
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

      console.log(token);
      console.log("[USER ID]:", userId);

      // Get user ID from token and set it in state
      const userIdFromToken = parseInt(parseJwt(token).sub);
      setUserId(userIdFromToken);

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
            username: friend.to.username,
            displayName: friend.to.username, // *** TEMPORARY: Change to displayName once implemented ***
            profilePicture: require("../../images/defaultprofile.png"), // *** TEMPORARY: Change to friend.to.profilePicture once implemented ***
          }));

          setFriends(friendData);
          console.log("[FRIENDS]", friendData);
        } else {
          setError("Error loading friends");
        }
      } catch (err) {
        setError("Error loading friends");
      } finally {
        setLoading(false);
      }
    }

    fetchFriends();
  }, []);

  // Load/unload messages whenever a friend is selected
  useEffect(() => {
    if (selectedFriend) {
      loadMessages();
    } else {
      setMessages([]);
    }
  }, [selectedFriend]);

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
      } else {
        setError("Error loading messages");
      }
    } catch (err) {
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

            {/* Display friends */}
            <ul className="list-unstyled">
              {friends.map((friend) => (
                <FriendCard
                  key={friend.id}
                  friend={friend}
                  onClick={() => {
                    setSelectedFriend(friend);
                  }}
                />
              ))}
            </ul>
          </div>

          {/* Right Sidebar */}
          <div className="col-9 d-flex flex-column h-100">
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
              </>
            ) : (
              <div className="d-flex flex-col justify-center items-center h-100 font-montserrat text-2xl font-semibold">
                <UserCircleIcon className="w-20 h-20" />
                <p>Select a message to view its content.</p>
              </div> // placeholder for selected DM
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
