import React, { useState } from "react";

export const SomeButton = () => {
  const [showText, setShowText] = useState(false);
  const [fetchedData, setFetchedData] = useState(null);
  const [error, setError] = useState(null);

  const handleClick = async () => {
    // If the button isn't clicked yet, fetch data - close text otherwise
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

  return (
    <div className="flex justify-center items-center space-x-4">
      {/* Left button */}
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
        onClick={() => console.log("Downloaded 8GB of RAM")}
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
        onClick={handleClick}
      >
        Posts
      </button>

      {/* Right button */}
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
        onClick={() => console.log("Downloaded 32GB of RAM")}
      >
        Comments
      </button>

      {/* Right most button */}
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
        onClick={() => console.log("Downloaded 64GB of RAM")}
      >
        Likes
      </button>

      {/* 5th button */}
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
        onClick={() => console.log("Downloaded 64GB of RAM")}
      >
        Friends
      </button>

      {/* 6th button */}
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
        onClick={() => console.log("Downloaded 64GB of RAM")}
      >
        Messages 
      </button>

      {/* 7th button */}
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
        onClick={() => console.log("Downloaded 64GB of RAM")}
      >
        Media
      </button>

      {/* 8th button */}
      <button
        className="relative
                overflow-hidden
                rounded-md
                shadow-md
                shadow-black/20
                bg-gradient-to-r from-green-700 to-lime-600
                py-3 px-4
                text-lg
                font-medium
                uppercase
                tracking-wider
                text-white
                hover:from-green-600 hover:to-lime-500"
        onClick={() => console.log("Downloaded 64GB of RAM")}
      >
        Clear
      </button>

      {showText && (
        <ul class="text-center mt-4 text-white max-w-lg">
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
        <p class="text-center mt-4 text-white max-w-lg">Error: {error}</p>
      )}
    </div>
  );
};
