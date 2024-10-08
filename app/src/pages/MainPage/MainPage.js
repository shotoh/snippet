import React from "react";

import NavBar from "../../components/MainPage/NavBar";
import TrendingBar from "../../components/MainPage/TrendingBar";
import Feed from "../../components/MainPage/Feed";
import FriendsBar from "../../components/MainPage/FriendsBar";
import { useState } from 'react';
import PostCreator from "../../components/MainPage/PostCreator";

const MainPage = () => {
  const [showModal, setShowModal] = useState(false);

  const [trendingPosts, setTrendingPosts] = useState([]);
  const [trendingError, setTrendingError] = useState("");

  const [friends, setFriends] = useState([]);
  const [friendsError, setFriendsError] = useState("");

  const fetchPosts = async () => {
    const token = localStorage.getItem('authToken');
    if (!token) {
      setError('User is not authenticated');
      return;
    }

    try {
      const response = await fetch('/api/posts', {
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      });
      const result = await response.json();

      if (response.ok && result.status === 'success') {
        setPosts(result.data);
      } else {
        setError('Error loading posts');
      }
    } catch (err) {
      console.error('Error loading posts:', err);
      setError('Error loading posts');
    }
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };
  
  
  return (
    <div className="min-h-screen bg-slate-200 flex flex-col">
      <NavBar/>
      <div className="flex-grow grid grid-cols-12 gap-4 mt-4 pr-4">
        {/* Trending Bar */}
        <div className="col-span-3 bg-orange-400">
          <TrendingBar posts={trendingPosts} error={trendingError} />
        </div>

        {/* Feed */}
        <div className="col-span-6 bg-sky-500">
          <Feed />
        </div>

        {/* Friends Bar */}
        <div className="col-span-3 bg-purple-400">
          <FriendsBar  friends={friends} error={friendsError}/>
        </div>
      </div>

    </div>
  );
};

export default MainPage;
