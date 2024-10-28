import React, { useEffect, useRef } from "react";

// Main MessageBody Component
export default function MessageBody({
  selectedFriend,
  messages,
  userId,
  shouldScrollToBottom,
  setShouldScrollToBottom,
}) {
  const messagesContainerRef = useRef(null);
  const friend = {
    picture: selectedFriend.profilePicture,
  };

  // Scroll to the bottom when shouldScrollToBottom is true
  useEffect(() => {
    if (shouldScrollToBottom && messagesContainerRef.current) {
      messagesContainerRef.current.scrollTop =
        messagesContainerRef.current.scrollHeight;
      setShouldScrollToBottom(false); // Reset the flag
    }
  }, [shouldScrollToBottom, setShouldScrollToBottom]);

  return (
    <div ref={messagesContainerRef} className="flex-grow overflow-y-auto p-4">
      <div className="flex flex-col gap-1">
        {messages.map((message, index) => {
          const isSameSenderAsPrevious =
            index > 0 && messages[index - 1].from.id === message.from.id;

          const messageFromId = Number(message.from.id);
          const isSelf = messageFromId === Number(userId);

          return isSelf ? (
            <MessageBubbleSelf
              key={message.id}
              message={message.content}
              isSameSender={isSameSenderAsPrevious}
            />
          ) : (
            <MessageBubbleFriend
              key={message.id}
              message={message.content}
              isSameSender={isSameSenderAsPrevious}
              friend={friend}
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
function MessageBubbleFriend({ message, isSameSender, friend }) {
  return (
    <div className={`flex items-end ${isSameSender ? "mt-1" : "mt-3"}`}>
      <img
        src={friend.picture}
        alt="ProfilePic"
        className="w-10 h-10 rounded-full"
      />
      <div className="ml-2 border-2 border-secondaryLight font-montserrat px-4 py-2 rounded-lg max-w-xs">
        {message}
      </div>
    </div>
  );
}
