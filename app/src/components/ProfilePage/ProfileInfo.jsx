import React from "react";

const loremIpsum =
  "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

export default function ProfileInfo() {
  return (
    <div className="mt-20 md:mt-28 px-8">
      <h1 className="text-3xl font-bold font-montserrat">Some Guy</h1>
      <p className="text-xl text-gray-500 font-montserrat">@someguy1</p>
      <p className="mt-4 text-gray-700 font-montserrat max-w-lg mx-auto">
        {loremIpsum}
      </p>
    </div>
  );
}
