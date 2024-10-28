import React from "react";

const loremIpsum =
  "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

export default function ProfileInfo({
  username,
  handle,
  biography,
  id,
  friendCount,
  posts,
}) {
  return (
    <div className="mt-20 md:mt-28 px-8 leading-tight font-montserrat">
      <span className="text-4xl font-bold">{username}</span> <br />
      <span className="text-lg text-gray-500">@{handle}</span> <br />
      <div className="mt-1">
        <>
          <span className="text-lg font-bold">{friendCount} </span>
          <span>{friendCount === 1 ? " friend" : " friends"}</span>
        </>
        <>
          <span className="text-lg font-bold ml-4">{posts.length} </span>
          <span>{posts.length === 1 ? " post" : " posts"}</span>
        </>
      </div>
      <p className="mt-3 text-gray-700 font-montserrat max-w-lg mx-auto">
        {biography ? biography : loremIpsum}
      </p>
    </div>
  );
}
