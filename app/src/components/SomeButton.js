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

import { NavButton } from "./NavButton";

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
        <NavButton text="Users" onClick={() => handleClick("users")} />
        <NavButton text="Posts" onClick={() => handleClick("posts")} />
        <NavButton text="Comments" onClick={() => handleClick("comments")} />
        <NavButton
          text="Post Likes"
          onClick={() => handleClick("post-likes")}
        />
        <NavButton
          text="Comment Likes"
          onClick={() => handleClick("comment-likes")}
        />
        <NavButton text="Friends" onClick={() => handleClick("friends")} />
        <NavButton text="Messages" onClick={() => handleClick("messages")} />
        <NavButton text="Media" onClick={() => handleClick("media")} />
      </div>

      {error && <p className="text-center text-red-500 mt-4">Error: {error}</p>}

      {/* Conditionally render the correct table based on the selected endpoint */}
      {fetchedData.length > 0 && currentEndpoint === "users" && (
        <UserTable data={fetchedData} />
      )}
      {fetchedData.length > 0 && currentEndpoint === "posts" && (
        <PostTable data={fetchedData} />
      )}
      {fetchedData.length > 0 && currentEndpoint === "comments" && (
        <CommentTable data={fetchedData} />
      )}
      {fetchedData.length > 0 && currentEndpoint === "post-likes" && (
        <PostLikesTable data={fetchedData} />
      )}
      {fetchedData.length > 0 && currentEndpoint === "comment-likes" && (
        <CommentLikesTable data={fetchedData} />
      )}
      {fetchedData.length > 0 && currentEndpoint === "friends" && (
        <FriendsTable data={fetchedData} />
      )}
      {fetchedData.length > 0 && currentEndpoint === "messages" && (
        <MessagesTable data={fetchedData} />
      )}
      {fetchedData.length > 0 && currentEndpoint === "media" && (
        <MediaTable data={fetchedData} />
      )}
    </div>
  );
};
