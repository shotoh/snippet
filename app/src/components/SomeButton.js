import React, { useState } from "react";

export const SomeButton = () => {
  const [showText, setShowText] = useState(false);
  const [fetchedData, setFetchedData] = useState(null);
  const [error, setError] = useState(null);

  const clickPosts = async () => {
    // If the button isn't clicked yet, fetch data - close text otherwise
    if (!showText) {
      try {
        const response = await fetch("/api/posts");
        if (!response.ok) {
          throw new Error("Failed to fetch posts");
        }
        const data = await response.json();
        setFetchedData(data);
        setShowText(true);
        setError(null);
      } catch (error) {
        setError(error.message);
        setShowText(false);
      }
    } else {
      setShowText(false);
    }
  };

  const clickUsers = async () => {
    if (!showText) {
      try {
        const response = await fetch("/api/users");
        if (!response.ok) {
          throw new Error("Failed to fetch data");
        }
        const data = await response.json();
        setFetchedData(data);
        setShowText(true);
        setError(null);
      } catch (error) {
        setError(error.message);
        setShowText(false);
      }
    } else {
      setShowText(false);
    }
  };

  const clickComments = async () => {
    if (!showText) {
      try {
        const response = await fetch("/api/comments");
        if (!response.ok) {
          throw new Error("Failed to fetch data");
        }
        const data = await response.json();
        setFetchedData(data);
        setShowText(true);
        setError(null);
      } catch (error) {
        setError(error.message);
        setShowText(false);
      }
    } else {
      setShowText(false);
    }
  };

  const clickLikesComment = async () => {
    if (!showText) {
      try {
        const response = await fetch("/api/users");
        if (!response.ok) {
          throw new Error("Failed to fetch data");
        }
        const data = await response.json();
        setFetchedData(data);
        setShowText(true);
        setError(null);
      } catch (error) {
        setError(error.message);
        setShowText(false);
      }
    } else {
      setShowText(false);
    }
  };

  const clickFriends = async () => {
    if (!showText) {
      try {
        const response = await fetch("/api/users");
        if (!response.ok) {
          throw new Error("Failed to fetch data");
        }
        const data = await response.json();
        setFetchedData(data);
        setShowText(true);
        setError(null);
      } catch (error) {
        setError(error.message);
        setShowText(false);
      }
    } else {
      setShowText(false);
    }
  };
  
  const clickMessages = async () => {
    if (!showText) {
      try {
        const response = await fetch("/api/users");
        if (!response.ok) {
          throw new Error("Failed to fetch data");
        }
        const data = await response.json();
        setFetchedData(data);
        setShowText(true);
        setError(null);
      } catch (error) {
        setError(error.message);
        setShowText(false);
      }
    } else {
      setShowText(false);
    }
  };

  const clickMedia = async () => {
    if (!showText) {
      try {
        const response = await fetch("/api/users");
        if (!response.ok) {
          throw new Error("Failed to fetch data");
        }
        const data = await response.json();
        setFetchedData(data);
        setShowText(true);
        setError(null);
      } catch (error) {
        setError(error.message);
        setShowText(false);
      }
    } else {
      setShowText(false);
    }
  };

  const clickCommentLikes = async () => {
    if (!showText) {
      try {
        const response = await fetch("/api/comment_likes");
        if (!response.ok) {
          throw new Error("Failed to fetch data");
        }
        const data = await response.json();
        setFetchedData(data);
        setShowText(true);
        setError(null);
      } catch (error) {
        setError(error.message);
        setShowText(false);
      }
    } else {
      setShowText(false);
    }
  }

  const clickPostLikes = async () => {
    if (!showText) {
      try {
        const response = await fetch("/api/post_likes");
        if (!response.ok) {
          throw new Error("Failed to fetch data");
        }
        const data = await response.json();
        setFetchedData(data);
        setShowText(true);
        setError(null);
      } catch (error) {
        setError(error.message);
        setShowText(false);
      }
    } else {
      setShowText(false);
    }
  }



  return (
    <div className="flex justify-center items-center space-x-4">
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
        onClick={clickUsers}
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
        onClick={clickPosts}
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
        onClick={clickComments}
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
        onClick={clickLikesComment}
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
        onClick={clickFriends}
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
        onClick={clickMessages}
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
        onClick={clickMedia}
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
        onClick={clickCommentLikes}
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
        onClick={clickPostLikes}
      >
        Post likes
      </button>

      {showText && (
        <ul className="text-center mt-4 text-white max-w-lg">
          {fetchedData
            ? fetchedData.map((user) => (
                <li key={user.id}>
                  {user.name} - {user.email}
                </li>
              ))
            : "Loading..."}
        </ul>
      )}

      {error && (
        <p className="text-center mt-4 text-white max-w-lg">Error: {error}</p>
      )}
    </div>
  );
};