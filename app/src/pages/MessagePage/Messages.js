import React, { useState, useEffect } from "react";
import NavBar from "../../components/MainPage/NavBar";
import { InputGroup, Form } from "react-bootstrap";
import { UserCircleIcon } from "@heroicons/react/24/solid";
import MessageHeader from "../../components/MessagePage/MessageHeader";
import MessageBody from "../../components/MessagePage/MessageBody";
import MessageBar from "../../components/MessagePage/MessageBar";
import FriendCard from "../../components/MessagePage/FriendCard";
import defaultProfile from "../../images/defaultprofile.png";
import {
  fetchUserDetails,
  fetchFriends,
  fetchMessages,
  sendMessage,
  getToken
} from "../../api/MessageAPI";

export default function MessagesPage() {
  // Friends of user
  const [friends, setFriends] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  const [userId, setUserId] = useState(null);
  const [username, setUsername] = useState(""); // State to store username

  const [selectedFriend, setSelectedFriend] = useState(null);
  const [messages, setMessages] = useState([]);
  const [shouldScrollToBottom, setShouldScrollToBottom] = useState(false);

  useEffect(() => {
    //Load user data and friends 
    const loadUserData = async () => {
      try {
        const { userId, username } = await fetchUserDetails();
        setUserId(userId);
        setUsername(username);

        const friendsData = await fetchFriends();
        setFriends(friendsData);
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };

    loadUserData();
  }, []);

  useEffect(() => {
    let intervalId;
    if (selectedFriend) {
      loadMessages(true);
      intervalId = setInterval(() => {
        loadMessages(false);
      }, 5000);
    } else {
      setMessages([]);
    }

    return () => clearInterval(intervalId);
  }, [selectedFriend]);

  // Load messages for selected friend
  const loadMessages = async (shouldScroll = false) => {
    if (!userId || !selectedFriend) {
      console.error("User ID is not available.");
      return;
    }

    try {
      const newMessages = await fetchMessages(userId, selectedFriend.id);
      if (JSON.stringify(messages) !== JSON.stringify(newMessages)) {
        setMessages(newMessages);
        setShouldScrollToBottom(shouldScroll);
      }
    } catch (error) {
      setError("Error loading message");
    }
  };

  // Handler for new messages sent by the user
  const handleNewMessage = async (messageContent) => {
    try {
      const newMessage = await sendMessage(userId, selectedFriend.id, messageContent);
      setMessages((prevMessages) => [...prevMessages, newMessage]);
      setShouldScrollToBottom(true); // Scroll to bottom when user sends a message
    } catch (error) {
      setError("Error sending message");
    }
  };

  return (
    <div>
      {/* Navbar */}
      <NavBar username={username} /> 
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
            <div className="border-b-2 border-secondaryLight">
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
            </div>

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
                <MessageBody
                  selectedFriend={selectedFriend}
                  messages={messages}
                  userId={userId}
                  shouldScrollToBottom={shouldScrollToBottom}
                  setShouldScrollToBottom={setShouldScrollToBottom}
                />

                <MessageBar
                  selectedFriend={selectedFriend}
                  userId={userId}
                  authToken={getToken()}
                  onNewMessage={handleNewMessage}
                />
              </>
            ) : (
              <div className="d-flex flex-col justify-center items-center h-100 font-montserrat text-2xl font-semibold">
                <UserCircleIcon className="w-20 h-20" />
                <p>Select a message to view its content.</p>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}