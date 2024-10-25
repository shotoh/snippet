// src/hooks/useProfileData.js

import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { parseJwt, fetchUserAndPosts } from "../api/ProfileAPI";
import DefaultProfilePicture from "../images/defaultprofile2.jpg";
import DefaultBanner from "../images/somepicture.jpg";

const useProfileData = () => {
  const [userData, setUserData] = useState({
    username: "",
    handle: "",
    biography: "",
    profilePicture: DefaultProfilePicture,
    profileBanner: DefaultBanner,
  });
  const [posts, setPosts] = useState([]);
  const [error, setError] = useState("");

  const token = localStorage.getItem("authToken");
  const { userId: userIdFromParams } = useParams();
  const userIdFromToken = parseJwt(token);
  const userIdToDisplay = userIdFromParams || userIdFromToken;

  const fetchData = async () => {
    if (!token || !userIdToDisplay) {
      setError("User is not authenticated");
      return;
    }

    try {
      const { userData: userResponse, userPosts } = await fetchUserAndPosts(
        userIdToDisplay,
        token
      );

      setUserData({
        username: userResponse.data.username || "",
        handle: userResponse.data.username || "",
        biography: userResponse.data.biography || "",
        profilePicture:
          userResponse.data.profilePicture || DefaultProfilePicture,
        profileBanner: userResponse.data.profileBanner || DefaultBanner,
      });

      setPosts(userPosts);
    } catch (error) {
      console.error("Error fetching data:", error);
      setError("Error loading user data");
    }
  };

  useEffect(() => {
    fetchData();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [userIdToDisplay, token]);

  return {
    userData,
    posts,
    error,
    fetchData,
    userIdToDisplay,
    token,
  };
};

export default useProfileData;
