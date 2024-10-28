import React from "react";

export default function ProfileBanner({ banner, profilePicture }) {
  return (
    <div className="relative w-full">
      <img
        src={banner}
        alt="Profile Banner"
        className="w-full h-32 md:h-44 object-cover"
      />
      <div className="absolute bottom-0 right-1/2 transform translate-y-1/2">
        <img
          src={profilePicture}
          alt="Profile"
          className="rounded-full w-32 h-32 md:w-40 md:h-40 object-cover border-4 border-white"
        />
      </div>
    </div>
  );
}
