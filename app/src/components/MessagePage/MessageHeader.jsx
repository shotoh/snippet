import React from "react";

export default function MessageHeader({ selectedFriend }) {
  const friend = {
    name: selectedFriend.displayName,
    handle: selectedFriend.username,
    picture: selectedFriend.profilePicture,
  };

  return (
    <div className="flex items-center w-full h-28 border-b-2 bg-slate-100 border-secondaryLight px-4">
      {/* Profile Picture */}
      <img
        src={friend.picture}
        alt={`${friend.name}'s profile`}
        className="w-20 h-20 rounded-full mr-4 object-cover"
      />

      {/* Name and Handle */}
      <div className="flex flex-col justify-center">
        <p className="text-4xl font-montserrat font-bold mt-3 mb-0">
          {friend.name}
        </p>
        <p className="text-xl font-montserrat text-gray-500 mt-0">
          @{friend.handle}
        </p>
      </div>
    </div>
  );
}
