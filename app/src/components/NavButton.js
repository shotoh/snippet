import React from "react";

export const NavButton = ({ text, onClick }) => {
  return (
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
      onClick={onClick}
    >
      {text}
    </button>
  );
};
