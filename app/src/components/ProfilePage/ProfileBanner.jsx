import React from "react";

export default function ProfileBanner() {
  return (
    <div className="relative w-full">
      <img
        src={require("../../images/somepicture.jpg")}
        alt="Profile Banner"
        className="w-full h-44 object-cover"
      />

      <div className="absolute -bottom-24 left-12">
        {" "}
        {/* Left margin added here */}
        <img
          src={require("../../images/jackblack.jpg")}
          alt="Profile"
          className="rounded-full w-48 h-48 object-cover"
        />
      </div>
    </div>
  );
}
