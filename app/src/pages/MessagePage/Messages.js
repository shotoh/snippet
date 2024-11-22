import React, { useState } from "react";
import NavBar from "../../components/MainPage/NavBar";
import MessageHeader from "../../components/MessagePage/MessageHeader";
import MessageBody from "../../components/MessagePage/MessageBody";
import MessageBar from "../../components/MessagePage/MessageBar";
import FriendsList from "../../components/MessagePage/FriendsList";
import { UserCircleIcon } from "@heroicons/react/24/solid";
import useUserData from "../../hooks/useUserData";
import useMessagePolling from "../../hooks/useMessagePolling";
import { sendMessage, getToken } from "../../api/MessageAPI";

export default function MessagesPage() {
  const { userId, username, friends, error } = useUserData();
  const [selectedFriend, setSelectedFriend] = useState(null);
  const [messages, setMessages] = useState([]);
  const [shouldScrollToBottom, setShouldScrollToBottom] = useState(false);

  useMessagePolling(userId, selectedFriend, setMessages, setShouldScrollToBottom);

  const handleNewMessage = async (messageContent) => {
    try {
      const newMessage = await sendMessage(userId, selectedFriend.id, messageContent);
      setMessages((prevMessages) => [...prevMessages, newMessage]);
      setShouldScrollToBottom(true);
    } catch (error) {
      console.error("Error sending message:", error);
    }
  };

  return (
    <div>
      <NavBar username={username} /> 
      <div className="container mx-auto mt-4 mb-4 border-2 p-0 border-secondaryLight" style={{ height: "calc(100vh - 100px)" }}>
        <div className="d-flex h-100">
          <div className="col-3 border-r-2 border-secondaryLight pt-4 px-3" style={{ overflowY: "scroll" }}>
            <FriendsList friends={friends} onSelectFriend={setSelectedFriend} />
          </div>

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