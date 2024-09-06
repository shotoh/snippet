import React, { useState } from "react";

export const SomeButton = () => {
  const [showText, setShowText] = useState(false);
  const [fetchedData, setFetchedData] = useState(null);
  const [error, setError] = useState(null);

  const handleClick = async () => {
    // If the button isn't clicked yet, fetch data - close text otherwise
    if (!showText) {
      try {
        const response = await fetch("http://localhost:8080/users");
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
    <div className="flex flex-col justify-center items-center">
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
        Download 16GB RAM
      </button>

      {showText && (
        <p className="text-center mt-4 text-white max-w-lg">
          {/*Display fetched data*/}
          {fetchedData ? JSON.stringify(fetchedData) : "Loading..."}
        </p>
      )}

      {error && (
        <p className="text-center mt-4 text-white max-w-lg">Error: {error}</p>
      )}
    </div>
  );
};
