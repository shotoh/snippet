import { uploadProfilePicture } from "./ImageAPI";

/**
 * Retrieve a user's ID based on their login token
 */
export const parseJwt = (token) => {
  try {
    const base64Url = token.split(".")[1];
    const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
    return parseInt(JSON.parse(window.atob(base64)).sub);
  } catch (error) {
    return null;
  }
};

/**
 * Retrieve user data from the server based on ID
 */
export const getUserData = async (userID, token) => {
  try {
    const response = await fetch(`/api/users/${userID}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (!response.ok) {
      throw new Error("User not found");
    }

    const data = await response.json();
    return data;
  } catch (err) {
    console.error(err);
  }
};

/**
 * Retrieve user's posts based on ID
 */
export const getUserPosts = async (userID, token) => {
  if (!token) {
    throw new Error("User is not authenticated");
  }

  try {
    const response = await fetch(`/api/posts?user=${userID}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    const result = await response.json();
    console.log("result", result);

    if (response.ok && result.status === "success") {
      return result.data;
    } else {
      throw new Error("Error fetching posts");
    }
  } catch (error) {
    console.error("Error fetching posts:", error);
    throw error;
  }
};

/**
 * Retrieve user's friend based on ID
 */
export const getFriendData = async (userID, token) => {
  try {
    const response = await fetch(`/api/friends?from=${userID}`, {
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
        displayName: friend.to.displayName,
        profilePicture: friend.to.profilePicture,
      }));

      return friendData;
    } else {
      throw new Error("Error loading friends");
    }
  } catch (err) {
    console.error(err);
  }
};

/**
 * Retrieve user's data and posts based on ID
 */
export const fetchUserAndPosts = async (userID, token) => {
  try {
    const [userData, userPosts] = await Promise.all([
      getUserData(userID, token),
      getUserPosts(userID, token),
    ]);

    return {
      userData,
      userPosts,
    };
  } catch (error) {
    console.error("Error fetching user data and posts:", error);
    throw error;
  }
};

/**
 * Update user's profile data
 * @param data - { displayName, biography, profilePicture }
 */
export const updateUserData = async (userID, token, data) => {
  if (!token) {
    throw new Error("User is not authenticated");
  }

  if (!data) {
    throw new Error("No data to update");
  }

  try {
    let jsonBody;
    if (!data.biography && !data.displayName && !data.profilePicture) {
      return;
    } else if (!data.biography) {
      jsonBody = JSON.stringify({
        displayName: data.displayName,
      });
    } else if (!data.displayName) {
      jsonBody = JSON.stringify({
        biography: data.biography,
      });
    } else {
      jsonBody = JSON.stringify({
        biography: data.biography,
        displayName: data.displayName,
      });
    }

    // Update profile info
    console.log(data.displayName + ", " + data.biography);
    await fetch(`/api/users/${userID}`, {
      method: "PATCH",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      body: jsonBody,
    });
  } catch (err) {
    console.error(err);
  }
  if (data.profilePicture) {
    await uploadProfilePicture(data.profilePicture, userID, token);
  }
};

export const createFriendRequest = async (targetUserID, token) => {
  try {
    let url = `/api/friends`;

    let response = await fetch(url, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        toId: targetUserID, // The body for the friend creation
      }),
    });
    console.log("response ok: " + response.ok);

    const result = await response.json();

    if (response.ok && result.status === "success") {
      console.log("worked!");
      window.location.reload(); // Refreshes page
    }
  } catch (err) {
    console.error("error creating friend request:", err);
  }
};

/**
 * Change user password - Used in SettingsPopup.jsx
 * @param {string} currentPassword
 * @param {string} newPassword
 * @param {string} token
 */
export const changeUserPassword = async (currentPassword, newPassword, token) => {
  if (!token) {
    console.error("User isn't authenticated, missing token");
    throw new Error("User is not authenticated");
  }
  
  try {
    const response = await fetch("/api/auth/change-password", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({
        oldPassword: currentPassword,
        newPassword: newPassword,
      }),
    });

    console.log("Server response status: ", response.status);

    //If the response isn't 204
    if (!response.ok) {
      let errorMessage = "Unknown error occurred";

      try {
        const result = await response.json();
        errorMessage = result?.data?.authorization || result?.message || errorMessage;
      } catch (error) {
        console.warn("Response does't have JSON.");
      }

      console.error("Password change failed: ", errorMessage);
      throw new Error(`Password change failed: ${errorMessage}`);
    }

    console.log("Password changed successfully.");
    return {status: "success"};
  } catch (error) {
    console.error("Error occurred while changing password: ", error);
    throw error;
  }
};