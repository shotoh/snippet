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

      console.log(friendData);

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
