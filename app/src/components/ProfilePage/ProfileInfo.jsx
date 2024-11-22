import React from "react";
import { Button } from "react-bootstrap";

const loremIpsum =
  "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

export default function ProfileInfo({
  username,
  displayName,
  handle,
  biography,
  id,
  friendCount,
  posts,
  buttonShown,
  openModal,
  addFriend,
  removeFriend,
}) {
  return (
    <div className="mt-20 md:mt-28 px-8 leading-tight font-montserrat">
      <span className="text-4xl font-bold">{}</span> <br />
      <div className="flex flex-row justify-between">
        <h1 className="text-4xl font-bold font-montserrat">
          {displayName ? displayName : username}
        </h1>
        {buttonShown === 0 && (
          <Button
            variant="success"
            onClick={addFriend}
            size="sm"
            className="w-auto mx-auto"
          >
            Add Friend
          </Button>
        )}
        {buttonShown === 1 && (
          <Button
            variant="danger"
            onClick={removeFriend}
            size="sm"
            className="w-auto mx-auto"
          >
            Remove Friend
          </Button>
        )}
        {buttonShown === 2 && (
          <Button className=" mxl-auto" onClick={openModal}>
            Edit
          </Button>
        )}
      </div>
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
        {biography}
      </p>
    </div>
  );
}
