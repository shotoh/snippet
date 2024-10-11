import React, { useEffect, useRef } from "react";

export default function MessageBody({ selectedMessage }) {
  const dummyMessages = [
    { sender: "friend", text: "Hey! How's it going?" },
    { sender: "friend", text: "Just wanted to check in." },
    { sender: "self", text: "Not bad, just working on my project." },
    {
      sender: "self",
      text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
    },
    {
      sender: "self",
      text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
    },
    {
      sender: "self",
      text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
    },
    {
      sender: "self",
      text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
    },
    { sender: "friend", text: "Let me know if you need help." },
  ];

  // Reference to the messages container
  const messagesContainerRef = useRef(null);

  // Scroll to the bottom when the component mounts
  useEffect(() => {
    if (messagesContainerRef.current) {
      messagesContainerRef.current.scrollTop =
        messagesContainerRef.current.scrollHeight;
    }
  }, []);

  return (
    <div ref={messagesContainerRef} className="flex-grow overflow-y-auto p-4">
      <div className="flex flex-col gap-1">
        {dummyMessages.map((message, index) => {
          const isSameSenderAsPrevious =
            index > 0 && dummyMessages[index - 1].sender === message.sender;

          return message.sender === "self" ? (
            <MessageBubbleSelf
              key={index}
              message={message.text}
              isSameSender={isSameSenderAsPrevious}
            />
          ) : (
            <MessageBubbleFriend
              key={index}
              message={message.text}
              isSameSender={isSameSenderAsPrevious}
            />
          );
        })}
      </div>
    </div>
  );
}

// MessageBubble for the User's Messages
function MessageBubbleSelf({ message, isSameSender }) {
  return (
    <div
      className={`bg-primaryLight text-white font-montserrat px-4 py-2 rounded-lg max-w-xs ${
        isSameSender ? "mt-1" : "mt-3"
      } self-end`}
    >
      {message}
    </div>
  );
}

// MessageBubble for the Friend's Messages
function MessageBubbleFriend({ message, isSameSender }) {
  return (
    <div
      className={`border-2 border-secondaryLight font-montserrat px-4 py-2 rounded-lg max-w-xs ${
        isSameSender ? "mt-1" : "mt-3"
      } self-start`}
    >
      {message}
    </div>
  );
}
