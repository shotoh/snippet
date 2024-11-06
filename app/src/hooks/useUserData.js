import { useState, useEffect } from "react";
import { fetchUserDetails, fetchFriends } from "../api/MessageAPI";

export default function useUserData() {
  const [userId, setUserId] = useState(null);
  const [username, setUsername] = useState("");
  const [friends, setFriends] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const loadUserData = async () => {
      try {
        const { userId, username } = await fetchUserDetails();
        setUserId(userId);
        setUsername(username);

        const friendsData = await fetchFriends();
        setFriends(friendsData);
      } catch (error) {
        setError(error.message);
      }
    };

    loadUserData();
  }, []);

  return { userId, username, friends, error };
}
