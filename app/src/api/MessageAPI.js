import defaultProfile from "../images/defaultprofile.png";

/**
 * Retrieve token from localStorage
 */
export const getToken = () => localStorage.getItem("authToken");

/**
 * Retrieve a user's ID based on their login token
 */
export const parseJwt = (token) => {
    try {
      const base64Url = token.split(".")[1];
      const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
      return JSON.parse(window.atob(base64));
    } catch (error) {
      return null;
    }
  };

/**
 * Fetch user details using the ID from the token
 */
export const fetchUserDetails = async () => {
const token = getToken();
if (!token) throw new Error("User is not authenticated");

const userId = parseJwt(token)?.sub;
if (!userId) throw new Error("Invalid token");

try {
    const response = await fetch(`/api/users/${userId}`, {
    headers: { Authorization: `Bearer ${token}` },
    });
    const result = await response.json();

    if (response.ok && result.status === "success") {
    return { userId, username: result.data.username };
    } else {
    throw new Error("Error loading user details");
    }
} catch (error) {
    console.error("Error loading user details:", error);
    throw error;
}
};

/**
 * Fetch friends associated with the user
 */
export const fetchFriends = async () => {
const token = getToken();
const userId = parseJwt(token)?.sub;
if (!token || !userId) throw new Error("User is not authenticated");

try {
    const response = await fetch(`/api/friends?from=${userId}`, {
    headers: { Authorization: `Bearer ${token}` },
    });
    const result = await response.json();

    if (response.ok && result.status === "success") {
    return result.data
        .filter((friend) => friend.status === "FRIEND")
        .map((friend) => ({
        id: friend.to.id,
        username: friend.to.username,
        displayName: friend.to.username,
        profilePicture: friend.to.profilePicture || defaultProfile,
        }));
    } else {
    throw new Error("Error loading friends");
    }
} catch (error) {
    console.error("Error loading friends:", error);
    throw error;
}
};

/**
 * Fetch messages between the user and a selected friend
 */
export const fetchMessages = async (userId, friendId) => {
const token = getToken();
if (!token) throw new Error("User is not authenticated");

try {
    const response = await fetch(`/api/messages?from=${userId}&to=${friendId}`, {
    headers: { Authorization: `Bearer ${token}` },
    });
    const result = await response.json();

    if (response.ok && result.status === "success") {
    return result.data;
    } else {
    throw new Error("Error loading messages");
    }
} catch (error) {
    console.error("Error loading messages:", error);
    throw error;
}
};

/**
 * Send a new message to a friend
 */
export const sendMessage = async (userId, friendId, messageContent) => {
const token = getToken();
if (!token) throw new Error("User is not authenticated");

try {
    const response = await fetch("/api/messages", {
    method: "POST",
    headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify({
        fromId: userId,
        toId: friendId,
        content: messageContent,
    }),
    });
    const result = await response.json();

    if (response.ok && result.status === "success") {
    return result.data;
    } else {
    throw new Error("Failed to send message");
    }
} catch (error) {
    console.error("Error sending message:", error);
    throw error;
}
};