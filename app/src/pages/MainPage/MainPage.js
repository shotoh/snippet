import React, { useState, useEffect } from "react";
import NavBar from "../../components/MainPage/NavBar";
import TrendingBar from "../../components/MainPage/TrendingBar";
import Feed from "../../components/MainPage/Feed";
import FriendsBar from "../../components/MainPage/FriendsBar";
import {
  fetchPosts,
  fetchTrendingPosts,
  fetchFriendsData,
  createFriendRequest,
  rejectFriendRequest,
  getToken,
  parseJwt,
} from "../../api/MainPageAPI";

// TO DO: Abstract friend related code into FriendBar API/Hooks
// Abstract trending posts related code into Trendingbar API/Hooks

const MainPage = () => {
  const [showModal, setShowModal] = useState(false);
  const [posts, setPosts] = useState([]);
  const [trendingPosts, setTrendingPosts] = useState([]);
  const [friends, setFriends] = useState([]);
  const [friendRequests, setFriendRequests] = useState([]);
  const [error, setError] = useState("");
  const [trendingError, setTrendingError] = useState("");
  const [friendsError, setFriendsError] = useState("");

  const token = getToken();
  const userId = parseJwt(token)?.sub;

  useEffect(() => {
    loadPosts();
    loadTrendingPosts();
    loadFriends();
    loadFriendRequests();
  }, []);

  const loadPosts = async () => {
    try {
      const postsData = await fetchPosts();
      console.log("Fetched post data: ", postsData);
      setPosts(postsData);
    } catch (error) {
      setError("Error loading posts");
    }
  };

  const loadTrendingPosts = async () => {
    try {
      const trendingData = await fetchTrendingPosts();
      setTrendingPosts(trendingData);
    } catch (error) {
      setTrendingError("Error loading trending posts");
    }
  };

  const loadFriends = async () => {
    try {
      const friendsData = await fetchFriendsData("FRIEND");
      setFriends(friendsData);
    } catch (error) {
      setFriendsError("Error loading friends");
    }
  };

  const loadFriendRequests = async () => {
    try {
      const friendRequestsData = await fetchFriendsData("PENDING");
      setFriendRequests(friendRequestsData);
    } catch (error) {
      setFriendsError("Error loading friend requests");
    }
  };

  return (
    <div className="min-h-screen bg-slate-200 flex flex-col">
      <NavBar onPostCreated={loadPosts} />
      <div className="flex-grow">
        <div className="max-w-screen-xl mx-auto grid grid-cols-12 gap-x-6 mt-4">
          {/* Trending Bar */}
          <div className="col-span-3 bg-white rounded-t-lg !bg-primaryLight border-t-8 border-r-2 border-l-2 border-secondaryLight min-h-screen">
            <TrendingBar posts={trendingPosts} error={trendingError} />
          </div>

          {/* Feed */}
          <div className="col-span-6 bg-sky-500">
            <Feed posts={posts} loadPosts={loadPosts} />
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
