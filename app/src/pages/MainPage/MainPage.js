import React, { useState, useEffect } from "react";
import NavBar from "../../components/MainPage/NavBar";
import TrendingBar from "../../components/MainPage/TrendingBar";
import Feed from "../../components/MainPage/Feed";
import FriendsBar from "../../components/MainPage/FriendsBar";

const MainPage = () => {
  const [posts, setPosts] = useState([]);
  const [error, setError] = useState('');

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

  useEffect(() => {
    fetchPosts(); 
  }, []);

  return (
    <div className="min-h-screen bg-slate-200 flex flex-col">
      <NavBar onPostCreated={fetchPosts} />
      <div className="flex-grow grid grid-cols-12 gap-4 mt-4 pr-4">
        <div className="col-span-3 bg-orange-400">
          <TrendingBar />
        </div>
        <div className="col-span-6 bg-sky-500">
          <Feed posts={posts} error={error} />
        </div>
        <div className="col-span-3 bg-purple-400">
          <FriendsBar />
        </div>
      </div>
    </div>
  );
};

export default MainPage;