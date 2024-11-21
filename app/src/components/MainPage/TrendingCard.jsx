import React from "react";
import defaultProfile from "../../images/defaultprofile.png";

const TrendingCard = ({ post }) => {
  const { user = {}, content = "No content available", images = [] } = post;

  //Navigate to user's profile 
  const handleProfileClick = () => {
    window.location.href = `/snippet/user/${user.id || ""}`;
  };

  return (
    <div className="bg-white shadow rounded-lg p-4 mb-4">
      <div className="flex items-center mb-4">
        {/* Profile picture */}
        <img
          src={user.profilePicture || defaultProfile} 
          alt={`${user.username || "Unknown User"}'s profile`}
          className="w-12 h-12 rounded-full cursor-pointer"
          onClick={handleProfileClick}
        />

        {/* User info */}
        <div className="ml-4">
          <p className="font-semibold text-gray-800">{user.username || "Unknown User"}</p>
        </div>
      </div>
      <p className="text-sm text-gray-600 mb-4">{content}</p>
      {images.length > 0 && (
        <div className="flex justify-center items-center">
          <img
            src={`/public/${images[0]}`} 
            alt="Post media"
            className="w-full max-w-[300px] h-auto object-cover rounded-md"
          />
        </div>
      )}
    </div>
  );
};

export default TrendingCard;