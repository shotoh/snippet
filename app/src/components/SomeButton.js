import React, { useState } from "react";
import {
  UserTable,
  PostTable,
  CommentTable,
  PostLikesTable,
  CommentLikesTable,
  FriendsTable,
  MessagesTable,
  MediaTable,
} from "./Tables";

export const SomeButton = () => {
  const [fetchedData, setFetchedData] = useState([]);
  const [error, setError] = useState(null);
  const [currentEndpoint, setCurrentEndpoint] = useState("");

  const handleClick = async (endpoint) => {
    setFetchedData([]);
    setError(null);
    setCurrentEndpoint(endpoint); // Update current endpoint for table display

    try {
      const response = await fetch(`/api/${endpoint}`);
      if (!response.ok) {
        throw new Error(`Failed to fetch ${endpoint}`);
      }
      const data = await response.json();
      setFetchedData(data.data); // Assuming 'data.data' contains the relevant array
    } catch (error) {
      setError(error.message);
      setFetchedData([]);
    }
  };

  return (
    <div className="flex flex-col items-center space-y-4">
      <div className="flex space-x-4">
        {/* Buttons for each endpoint */}
        <button onClick={() => handleClick('users')}>Users</button>
        <button onClick={() => handleClick('posts')}>Posts</button>
        <button onClick={() => handleClick('comments')}>Comments</button>
        <button onClick={() => handleClick('post-likes')}>Post Likes</button>
        <button onClick={() => handleClick('comment-likes')}>Comment Likes</button>
        <button onClick={() => handleClick('friends')}>Friends</button>
        <button onClick={() => handleClick('messages')}>Messages</button>
        <button onClick={() => handleClick('media')}>Media</button>
      </div>

      {error && <p className="text-center text-red-500 mt-4">Error: {error}</p>}

      {/* Conditionally render the correct table based on the selected endpoint */}
      {fetchedData.length > 0 && currentEndpoint === "users" && <UserTable data={fetchedData} />}
      {fetchedData.length > 0 && currentEndpoint === "posts" && <PostTable data={fetchedData} />}
      {fetchedData.length > 0 && currentEndpoint === "comments" && <CommentTable data={fetchedData} />}
      {fetchedData.length > 0 && currentEndpoint === "post-likes" && <PostLikesTable data={fetchedData} />}
      {fetchedData.length > 0 && currentEndpoint === "comment-likes" && <CommentLikesTable data={fetchedData} />}
      {fetchedData.length > 0 && currentEndpoint === "friends" && <FriendsTable data={fetchedData} />}
      {fetchedData.length > 0 && currentEndpoint === "messages" && <MessagesTable data={fetchedData} />}
      {fetchedData.length > 0 && currentEndpoint === "media" && <MediaTable data={fetchedData} />}
    </div>
  );
};