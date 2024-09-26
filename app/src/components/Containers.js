import React from 'react';
const macrosoftLogo = require("../images/macrosoftLogo.png");

export const TopPosts = () => {
  return (
    <div className="top-posts">
      <div className="media1">
        <img
          src={macrosoftLogo}
          alt="Macrosoft Logo"
          className="mr-2 w-12 h-12"
        />
    </div>
    </div>
  );
};

export const Feed = () => {
  return (
    <div className="feed">
      {/* Content for Feed container */}
    </div>
  );
};

export const Friends = () => {
  return (
    <div className="friends">
      {/* Content for Friends container */}
    </div>
  );
};