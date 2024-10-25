import React, { useState, useEffect } from "react";
import NavBar from "../../components/MainPage/NavBar";
import TrendingBar from "../../components/MainPage/TrendingBar";
import Feed from "../../components/MainPage/Feed";
import FriendsBar from "../../components/MainPage/FriendsBar";
import PostCreator from "../../components/MainPage/PostCreator";

const MainPage = () => {
  const [showModal, setShowModal] = useState(false);

  const [trendingPosts, setTrendingPosts] = useState([]);
  const [trendingError, setTrendingError] = useState("");

  const [friends, setFriends] = useState([]);
  const [friendsError, setFriendsError] = useState("");

  const [friendRequests, setFriendRequests] = useState([]);
  const [requested, setRequested] = useState([]);

  const [error, setError] = useState("");
  const [posts, setPosts] = useState([]);

  const [username, setUsername] = useState("");

  const authToken = localStorage.getItem("authToken");

  // Helper function to parse JWT token
  const parseJwt = (token) => {
    try {
      const base64Url = token.split(".")[1];
      const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
      return JSON.parse(window.atob(base64));
    } catch (error) {
      return null;
    }
  };

  const fetchPosts = async () => {
    const token = localStorage.getItem("authToken");
    if (!token) {
      setError("User is not authenticated");
      return;
    }

    try {
      const response = await fetch("/api/posts", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      const result = await response.json();

      if (response.ok && result.status === "success") {
        setPosts(result.data);
      } else {
        setError("Error loading posts");
      }
    } catch (err) {
      console.error("Error loading posts:", err);
      setError("Error loading posts");
    }
  };

  const fetchFriends = async () => {
    //WIP
    const token = authToken;

    // Get user ID from token and set it in state
    const userIdFromToken = parseInt(parseJwt(token).sub);

    console.log("[TOKEN]:", token);
    console.log("[USER ID]:", userIdFromToken);

    try {
      const response = await fetch(`/api/friends?from=${userIdFromToken}`, {
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

        setFriends(friendData);
        console.log("[FRIENDS]:", friendData);
      } else {
        setError("Error loading friends");
      }
    } catch (err) {
      console.error("Error loading friends:", err);
      setError("Error loading friends");
    }
  };

  const fetchFriendRequests = async () => {
    //WIP
    const token = authToken;

    // Get user ID from token and set it in state
    const userIdFromToken = parseInt(parseJwt(token).sub);

    console.log("[TOKEN]:", token);
    console.log("[USER ID]:", userIdFromToken);

    try {
      const response = await fetch(`/api/friends?to=${userIdFromToken}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      const result = await response.json();

      if (response.ok && result.status === "success") {
        let friendRequestList = result.data.filter(
          (friend) =>
            friend.status === "PENDING" && // Status should be "PENDING", not "FRIEND"
            friend.from.id !== userIdFromToken
        );

        const friendRequestData = friendRequestList.map((friend) => ({
          id: friend.id,
          friendId: friend.from.id,
          username: friend.from.username,
          displayName: friend.from.displayName,
          profilePicture: friend.from.profilePicture,
          status: friend.status,
        }));

        setFriendRequests(friendRequestData);

        console.log("[FRIEND REQUESTS]:", friendRequestData);
        console.log(result);
      } else {
        setError("Error loading friends");
      }
    } catch (err) {
      console.error("Error loading friends:", err);
      setError("Error loading friends");
    }
  };

  const createFriendRequest = async (targetUsername) => {
    console.log("Trying to find " + targetUsername);

    if (requested.includes(targetUsername)) {
      return "Already Requested";
    }

    const token = localStorage.getItem("authToken");
    try {
      //Find Friend
      let url = `/api/users`;

      let response = await fetch(url, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });

      if (!response.ok) {
        throw new Error(`Failed to fetch users: ${response.statusText}`);
      }
      const users = await response.json();
      const userData = users.data;

      // Find the user by the specific username
      const foundUser = userData.find(
        (user) => user.username === targetUsername
      );

      let userId = -1;
      if (foundUser) {
        userId = foundUser.id;
        console.log(`Found user ID: ${userId}`);
      } else {
        console.log("User not found");
        return "User Not Found";
      }

      //Make friends!
      url = `/api/friends`;

      response = await fetch(url, {
        method: "POST",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          toId: userId, // The body for the friend creation
        }),
      });
      console.log("response ok: " + response.ok);

      if (response.status === 409) {
        requested.push(targetUsername);
        return "Already Requested";
      }

      const result = await response.json();

      if (response.ok && result.status === "success") {
        console.log("worked!");
        console.log(result.data);
        fetchFriends();
        fetchFriendRequests();
        requested.push(targetUsername);
        return "Sent";
      }
    } catch (err) {
      console.error("error loading friends:", err);
      return "Failed";
    }
  };

  const rejectFriendRequest = async (targetUsername) => {
    console.log("Trying to remove request from " + targetUsername);
    try {
      //Find Friend
      let url = `/api/users`;
      const token = localStorage.getItem("authToken");
      let response = await fetch(url, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });

      if (!response.ok) {
        throw new Error(`Failed to fetch users: ${response.statusText}`);
      }
      const users = await response.json();
      const userData = users.data;

      // Find the user by the specific username
      const foundUser = userData.find(
        (user) => user.username === targetUsername
      );

      let userId = -1;
      if (foundUser) {
        userId = foundUser.id;
        console.log(`Found user ID: ${userId}`);
      } else {
        console.log("User not found");
        return "User Not Found";
      }

      const userIdFromToken = parseInt(parseJwt(token).sub);
      //Find ID of friending
      url = `/api/friends?from=${userId}`;
      response = await fetch(url, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });

      let result = await response.json();
      console.log(result.data);
      let friendID = -1;
      let friendRequestList = result.data;
      if (response.ok && result.status === "success") {
        friendID = findIdByFromId(result.data, userId);
        if (friendID != -1) {
          console.log("got friend entry ID: " + friendID);
        } else {
          return "Fail";
        }
      } else {
        console.log("couldn't get ID of friend entry");
        return "Fail";
      }

      //Delete friends
      url = `/api/friends/${friendID}`;

      response = await fetch(url, {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });
      console.log("response ok: " + response.ok);

      if (response.ok && result.status === "success") {
        console.log("worked!");
        console.log(result.data);
        fetchFriendRequests();
      }
    } catch (err) {
      console.error("error removing friend request:", err);
      return "Failed";
    }
  };

  const findIdByFromId = (data, fromId) => {
    const foundElement = data.find((element) => element.from.id === fromId);

    return foundElement ? foundElement.id : null;
  };

  const getUsername = async () => {
    //WIP
    const token = localStorage.getItem("authToken");
    try {
      //Find Friend
      let url = `/api/users`;

      let response = await fetch(url, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });

      if (!response.ok) {
        throw new Error(`Failed to fetch users: ${response.statusText}`);
      }
      const users = await response.json();
      const userData = users.data;

      const userIdFromToken = parseInt(parseJwt(token).sub);
      // Find the user by the specific username
      const foundUser = userData.find((user) => user.id === userIdFromToken);

      if (foundUser) {
        setUsername(foundUser.username);
      } else {
        console.log("User not found");
        return "User Not Found";
      }
    } catch (err) {
      console.error("error getting username:", err);
    }
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  useEffect(() => {
    fetchPosts();
    fetchFriends();
    fetchFriendRequests();
    getUsername();
  }, []);

  return (
    <div className="min-h-screen bg-slate-200 flex flex-col">
      <NavBar username={username} onPostCreated={fetchPosts} />
      <div className="flex-grow">
        <div className="max-w-screen-xl mx-auto grid grid-cols-12 gap-x-6 mt-4">
          {/* Trending Bar */}
          <div className="col-span-3 bg-white rounded-t-lg !bg-primaryLight border-t-8 border-r-2 border-l-2 border-secondaryLight min-h-screen">
            <TrendingBar posts={trendingPosts} error={trendingError} />
          </div>

          {/* Feed */}
          <div className="col-span-6 bg-sky-500">
            <Feed posts={posts} />
          </div>

          {/* Friends Bar */}
          <div className="col-span-3 bg-white rounded-t-lg !bg-primaryLight border-t-8 border-r-2 border-l-2 border-secondaryLight">
            <FriendsBar
              friends={friends}
              friendRequests={friendRequests}
              error={friendsError}
              sendFriendRequest={createFriendRequest}
              denyFriendRequest={rejectFriendRequest}
            />
          </div>
        </div>
      </div>
    </div>
  );
};
export default MainPage;
