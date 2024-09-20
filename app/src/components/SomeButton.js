import React, { useState } from "react";
import {
  UserTable,
  PostTable,
  CommentTable,
  PostLikesTable,
  CommentLikesTable,
  FriendsTable,
  MessagesTable,
  MediaTable
} from "./Tables";

export const SomeButton = () => {
  const [fetchedData, setFetchedData] = useState([]);
  const [headers, setHeaders] = useState([]);
  const [error, setError] = useState(null);
  const [currentEndpoint, setCurrentEndpoint] = useState("");

  const handleClick = async (endpoint) => {
    //Resets displayed data if any is being shown
    setFetchedData([]);
    setHeaders([]);
    setError(null);
    setCurrentEndpoint(endpoint);   //Establishes current endpoint being used for proper display of data in the table

    try {
      const response = await fetch(`/api/${endpoint}`);
      if (!response.ok) {
        throw new Error(`Failed to fetch ${endpoint}`);
      }
      const data = await response.json();
      console.log("Fetched data for endpoint:", endpoint, data);    //error handling

      if (data.data && data.data.length > 0) {      //Sets headers for table based on recieved data
        setHeaders(Object.keys(data.data[0]));
      }
      setFetchedData(data.data);
    } catch (error) {
      setError(error.message);
      setFetchedData([]);
      }
  };

  return (
    <div className="flex flex-col items-center space-y-4">
      <div className="flex space-x-4">
      <button
        className="relative
                overflow-hidden
                rounded-md
                shadow-md
                shadow-black/20
                bg-gradient-to-r from-green-700 to-lime-600
                py-3 px-5
                text-lg
                font-medium
                uppercase
                tracking-wider
                text-white
                hover:from-green-600 hover:to-lime-500"
        onClick={() => handleClick('users')}
      >
        Users
      </button>

      <button
        className="relative
                overflow-hidden
                rounded-md
                shadow-md
                shadow-black/20
                bg-gradient-to-r from-green-700 to-lime-600
                py-3 px-5
                text-lg
                font-medium
                uppercase
                tracking-wider
                text-white
                hover:from-green-600 hover:to-lime-500"
                onClick={() => handleClick('posts')}
      >
        Posts
      </button>

      <button
        className="relative
                overflow-hidden
                rounded-md
                shadow-md
                shadow-black/20
                bg-gradient-to-r from-green-700 to-lime-600
                py-3 px-5
                text-lg
                font-medium
                uppercase
                tracking-wider
                text-white
                hover:from-green-600 hover:to-lime-500"
        onClick={() => handleClick('comments')}
      >
        Comments
      </button>

      <button
        className="relative
                overflow-hidden
                rounded-md
                shadow-md
                shadow-black/20
                bg-gradient-to-r from-green-700 to-lime-600
                py-3 px-5
                text-lg
                font-medium
                uppercase
                tracking-wider
                text-white
                hover:from-green-600 hover:to-lime-500"
        onClick={() => handleClick('comment_likes')}
      >
        Comment Likes
      </button>

      <button
        className="relative
                overflow-hidden
                rounded-md
                shadow-md
                shadow-black/20
                bg-gradient-to-r from-green-700 to-lime-600
                py-3 px-5
                text-lg
                font-medium
                uppercase
                tracking-wider
                text-white
                hover:from-green-600 hover:to-lime-500"
        onClick={() => handleClick('friends')}
      >
        Friends
      </button>

      <button
        className="relative
                overflow-hidden
                rounded-md
                shadow-md
                shadow-black/20
                bg-gradient-to-r from-green-700 to-lime-600
                py-3 px-5
                text-lg
                font-medium
                uppercase
                tracking-wider
                text-white
                hover:from-green-600 hover:to-lime-500"
        onClick={() => handleClick('messages')}
      >
        Messages
      </button>

      <button
        className="relative
                overflow-hidden
                rounded-md
                shadow-md
                shadow-black/20
                bg-gradient-to-r from-green-700 to-lime-600
                py-3 px-5
                text-lg
                font-medium
                uppercase
                tracking-wider
                text-white
                hover:from-green-600 hover:to-lime-500"
        onClick={() => handleClick('media')}
      >
        Media
      </button>

      <button
        className="relative
                overflow-hidden
                rounded-md
                shadow-md
                shadow-black/20
                bg-gradient-to-r from-green-700 to-lime-600
                py-3 px-5
                text-lg
                font-medium
                uppercase
                tracking-wider
                text-white
                hover:from-green-600 hover:to-lime-500"
        onClick={() => handleClick('post-likes')}
      >
        Post likes
      </button>
      </div>

      {error && (
        <p className="text-center mt-4 text-red-500 max-w-lg">Error: {error}</p>
      )}

      {/* Table Rendering for each endpoint */}
      {fetchedData.length > 0 && currentEndpoint === "users" && (
        <UserTable data={fetchedData} headers={headers} />
      )}

      {fetchedData.length > 0 && currentEndpoint === "posts" && (
        <PostTable data={fetchedData} headers={headers} />
      )}

      {fetchedData.length > 0 && currentEndpoint === "comments" && (
        <CommentTable data={fetchedData} headers={headers} />
      )}

      {fetchedData.length > 0 && currentEndpoint === "post_likes" && (
        <PostLikesTable data={fetchedData} headers={headers} />
      )}

      {fetchedData.length > 0 && currentEndpoint === "comment-likes" && (
        <CommentLikesTable data={fetchedData} headers={headers} />
      )}
      
      {fetchedData.length > 0 && currentEndpoint === "friends" && (
        <FriendsTable data={fetchedData} headers={headers} />
      )}

      {fetchedData.length > 0 && currentEndpoint === "messages" && (
        <MessagesTable data={fetchedData} headers={headers} />
      )}

      {fetchedData.length > 0 && currentEndpoint === "media" && (
        <MediaTable data={fetchedData} headers={headers} />
      )}
    </div>
  );
};