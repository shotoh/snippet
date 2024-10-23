import React from "react";

export default function ProfileBanner() {
  return (
    <div className="relative w-full">
      <img
        src={require("../../images/somepicture.jpg")}
        alt="Profile Banner"
        className="w-full h-32 md:h-44 object-cover"
      />
      <div className="absolute bottom-0 left-1/2 transform -translate-x-1/2 translate-y-1/2">
        <img
          src={require("../../images/jackblack.jpg")}
          alt="Profile"
          className="rounded-full w-32 h-32 md:w-40 md:h-40 object-cover border-4 border-white"
        />
      </div>
    </div>
  );
}
