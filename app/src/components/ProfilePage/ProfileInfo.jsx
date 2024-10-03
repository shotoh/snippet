import React from "react";

const loremIpsum =
  "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.";

export default function ProfileInfo() {
  return (
    <div className="mt-28 ml-12">
      <h1 className="text-3xl font-bold font-montserrat">Some Guy</h1>
      <p className="text-xl text-gray-500 font-montserrat">@someguy1</p>
      <p className="mt-4 text-gray-700 font-montserrat max-w-lg">
        {loremIpsum}
      </p>
    </div>
  );
}
