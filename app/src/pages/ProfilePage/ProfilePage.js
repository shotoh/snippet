import React, { useState, useEffect } from "react";
import NavBar from "../../components/MainPage/NavBar";
import ProfileBanner from "../../components/ProfilePage/ProfileBanner";
import ProfileInfo from "../../components/ProfilePage/ProfileInfo";
import ProfileFeed from "../../components/ProfilePage/ProfileFeed";
import { fetchUserPosts } from "./FetchUserPosts";

const ProfilePage = () => {
  const [posts, setPosts] = useState([]);
  const [error, setError] = useState("");
  const [username, setUsername] = useState("");
  const [userId, setUserId] = useState(null);
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

  const fetchUserPostsAndUpdate = async () => {
    const token = authToken;
    if (!token) {
      setError("User is not authenticated");
      return;
    }

    try {
      const userIdFromToken = parseInt(parseJwt(token).sub);
      setUserId(userIdFromToken);
      console.log("[DEBUG] User ID from token:", userIdFromToken);

      const response = await fetch(`/api/users/${userIdFromToken}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      const result = await response.json();

      if (response.ok && result.status === "success") {
        setUsername(result.data.username);
        console.log("[DEBUG] Username:", result.data.username); 

        const userPosts = await fetchUserPosts(userIdFromToken);
        console.log("[DEBUG] Fetched posts for user:", userPosts); 
        setPosts(userPosts);
      } else {
        setError("Error loading user data");
      }
    } catch (err) {
      console.error("Error fetching user data:", err);
      setError("Error loading user data");
    }
  };

  useEffect(() => {
    fetchUserPostsAndUpdate();
  }, []);

  return (
    <div className="min-h-screen bg-slate-200 flex flex-col">
      <NavBar username={username} onPostCreated={fetchUserPostsAndUpdate} />
      <div className="flex-grow">
        <div className="max-w-screen-xl mx-auto px-4">
          <div className="grid grid-cols-12 gap-6 mt-4">
            {/* Left Column: Profile Banner and Info */}
            <div className="col-span-12 md:col-span-4">
              <div className="bg-white rounded-lg shadow-md overflow-hidden">
                <ProfileBanner />
                <ProfileInfo />
              </div>
            </div>
            {/* Right Column: User's Posts */}
            <div className="col-span-12 md:col-span-8">
              <div className="bg-white rounded-lg p-6 shadow-md">
                <ProfileFeed posts={posts} error={error} />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;