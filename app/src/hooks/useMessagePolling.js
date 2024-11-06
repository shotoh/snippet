import { useEffect } from "react";
import { fetchMessages } from "../api/MessageAPI";

export default function useMessagePolling(userId, selectedFriend, setMessages, setShouldScrollToBottom) {
  useEffect(() => {
    let intervalId;

    const loadMessages = async (shouldScroll = false) => {
      if (!userId || !selectedFriend) return;
      
      try {
        const newMessages = await fetchMessages(userId, selectedFriend.id);
        setMessages((prevMessages) => {
          if (JSON.stringify(prevMessages) !== JSON.stringify(newMessages)) {
            setShouldScrollToBottom(shouldScroll);
            return newMessages;
          }
          return prevMessages;
        });
      } catch (error) {
        console.error("Error loading messages:", error);
      }
    };

    if (selectedFriend) {
      loadMessages(true);
      intervalId = setInterval(() => loadMessages(false), 5000);
    } else {
      setMessages([]);
    }

    return () => clearInterval(intervalId);
  }, [userId, selectedFriend, setMessages, setShouldScrollToBottom]);
}