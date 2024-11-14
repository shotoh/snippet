import defaultProfile from "../images/defaultprofile.png";

/**
 * Retrieve the token from localStorage
 */
export const getToken = () => localStorage.getItem("authToken");

/**
 * Helper function to parse JWT token
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
 * Fetch posts from the server
 */
export const fetchPosts = async () => {
  const token = getToken();
  if (!token) {
    throw new Error("User is not authenticated");
  }

  try {
    const response = await fetch("/api/posts", {
      headers: { Authorization: `Bearer ${token}` },
    });
    const result = await response.json();

    if (response.ok && result.status === "success") {
      return result.data;
    } else {
      throw new Error("Error loading posts");
    }
  } catch (error) {
    console.error("Error loading posts:", error);
    throw error;
  }
};

/**
 * Fetch trending posts from the server
 */
export const fetchTrendingPosts = async () => {
  const token = getToken();
  if (!token) {
    throw new Error("User is not authenticated");
  }

  try {
    const response = await fetch("/api/trending", {
      headers: { Authorization: `Bearer ${token}` },
    });
    const result = await response.json();

    if (response.ok && result.status === "success") {
      return result.data;
    } else {
      throw new Error("Error loading trending posts");
    }
  } catch (error) {
    console.error("Error loading trending posts:", error);
    throw error;
  }
};

/**
 * Fetch friends or friend requests based on status
 */
export const fetchFriendsData = async (status = "FRIEND") => {
  const token = getToken();
  if (!token) {
    throw new Error("User is not authenticated");
  }

  const userId = parseJwt(token)?.sub;
  if (!userId) {
    throw new Error("Invalid token");
  }

  try {
    const response = await fetch(`/api/friends?from=${userId}`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    const result = await response.json();

    if (response.ok && result.status === "success") {
      return result.data
        .filter((friend) => friend.status === status)
        .map((friend) => ({
          id: friend.to.id,
          username: friend.to.username,
          displayName: friend.to.username,
          profilePicture: friend.to.profilePicture || defaultProfile,
        }));
    } else {
      throw new Error("Error loading friends data");
    }
  } catch (error) {
    console.error("Error loading friends data:", error);
    throw error;
  }
};

/**
 * Send a friend request to a user
 */
export const createFriendRequest = async (targetUsername) => {
  const token = getToken();
  if (!token) {
    throw new Error("User is not authenticated");
  }

  try {
    const userResponse = await fetch(`/api/users`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    });

    if (!userResponse.ok) throw new Error("Failed to fetch users");

    const users = await userResponse.json();
    const foundUser = users.data.find(
      (user) => user.username === targetUsername
    );

    if (!foundUser) return "User Not Found";

    const friendResponse = await fetch(`/api/friends`, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ toId: foundUser.id }),
    });

    if (friendResponse.status === 409) return "Already Requested";

    const result = await friendResponse.json();
    if (friendResponse.ok && result.status === "success") {
      return "Sent";
    }
  } catch (error) {
    console.error("Error sending friend request:", error);
    return "Failed";
  }
};

/**
 * Reject a friend request
 */
export const rejectFriendRequest = async (targetUsername) => {
  const token = getToken();
  if (!token) {
    throw new Error("User is not authenticated");
  }

  try {
    const userResponse = await fetch(`/api/users`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    });

    if (!userResponse.ok) throw new Error("Failed to fetch users");

    const users = await userResponse.json();
    const foundUser = users.data.find(
      (user) => user.username === targetUsername
    );

    if (!foundUser) return "User Not Found";

    const userId = parseJwt(token)?.sub;
    const friendDataResponse = await fetch(
      `/api/friends?from=${foundUser.id}`,
      {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      }
    );

    const result = await friendDataResponse.json();
    const friendID = result.data.find(
      (element) => element.from.id === userId
    )?.id;

    if (!friendID) return "Failed";

    const deleteResponse = await fetch(`/api/friends/${friendID}`, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    });

    if (deleteResponse.ok) {
      return "Request Rejected";
    } else {
      throw new Error("Error rejecting friend request");
    }
  } catch (error) {
    console.error("Error rejecting friend request:", error);
    return "Failed";
  }
};
