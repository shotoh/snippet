import React, { useEffect, useRef } from "react";

// Main MessageBody Component
export default function MessageBody({ messages, userId }) {
  const messagesContainerRef = useRef(null);

  // Scroll to the bottom when messages change
  useEffect(() => {
    if (messagesContainerRef.current) {
      messagesContainerRef.current.scrollTop =
        messagesContainerRef.current.scrollHeight;
    }
  }, [messages]);

  return (
    <div ref={messagesContainerRef} className="flex-grow overflow-y-auto p-4">
      <div className="flex flex-col gap-1">
        {messages.map((message, index) => {
          // Determine if the sender of the current message is the same as the previous message
          const isSameSenderAsPrevious =
            index > 0 && messages[index - 1].from.id === message.from.id;

          // Check if the message was sent by the current user
          const isSelf = message.from.id === userId;

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
