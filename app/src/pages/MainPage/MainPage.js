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

  const [error, setError] = useState("");
  const [posts, setPosts] = useState([]);

  

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

  const fetchFriends = async(username) => {
    //WIP
    const token = localStorage.getItem('authToken');
    try {

      const url = `/api/friends?from=${username}`;


      const response = await fetch(url, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,
        }
      });

      const result = await response.json();

      if(response.ok && result.status === 'success') {
        console.log("worked!");
        console.log(result.data);
        setFriends(result.data);
      }


    } catch(err) {
      console.error("error loading friends:", err);

    }
    
  }

  const createFriendRequest = async (username) => {
    console.log(username);
    //WIP
    const token = localStorage.getItem('authToken');
    try {

      const url = `/api/friends`;


      const response = await fetch(url, {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify({
          toId: {username}, // The body for the friend creation
        }),
      });
      console.log("response ok: " + response.ok);
      const result = await response.json();

      if(response.ok && result.status === 'success') {
        console.log("worked!");
        console.log(result.data);
        setFriends(result.data);
      }


    } catch(err) {
      console.error("error loading friends:", err);

    }
  }

  const handleCloseModal = () => {
    setShowModal(false);
  };
  
  useEffect(() => {
    fetchPosts(); // Fetch posts when the component loads
  }, []);

  return (
    <div className="min-h-screen bg-slate-200 flex flex-col">
      <NavBar/>
      <div className="flex-grow grid grid-cols-12 gap-3 mt-4 pr-4">
        {/* Trending Bar */}
        <div className="col-span-3 bg-orange-400">
          <TrendingBar posts={trendingPosts} error={trendingError} />
        </div>

        {/* Feed */}
        <div className="col-span-6 bg-sky-500">
          <Feed posts={posts}/>
        </div>

        {/* Friends Bar */}
        <div className="col-span-3 bg-white rounded-lg  !bg-primaryLight border-t-8 border-r-2 border-l-2 border-secondaryLight mb-4"
          style={{"height":"85vh"}}
        >
          <FriendsBar  friends={friends} error={friendsError} sendFriendRequest={createFriendRequest}/>
        </div>
      </div>

    </div>
  );
};

export default MainPage;
