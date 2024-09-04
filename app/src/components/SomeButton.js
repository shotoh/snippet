import React, { useState } from "react";

export const SomeButton = () => {
  const [showText, setShowText] = useState(false);

  const handleClick = () => {
    // Update this function to await for backend response
    setShowText(!showText);
  };

  return (
    <div className="flex flex-col justify-center items-center">
      <button
        class="relative
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
        <p class="text-center mt-4 text-white max-w-lg">
          arghhahghahrhhghhghdfhdfhhghhghhfhshhshdhsgfgsjsjdhh
        </p>
      )}
    </div>
  );
};
