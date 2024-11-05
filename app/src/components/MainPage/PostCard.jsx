import React from "react";
import { Carousel } from "react-bootstrap";
import {
  HandThumbUpIcon,
  HandThumbDownIcon,
  ChatBubbleLeftIcon,
} from "@heroicons/react/24/solid";

import MediaPlaceholder from "../../images/nomedia.jpg";
import DefaultProfilePicture from "../../images/defaultprofile2.jpg";

// ** TO DO: Figure out importing react-icons instead of using heroicons because holy shit it looks so bad dude I can't stand it

/**
 * Template for a post
 * @param post - { user, media, text, likes, dislikes, comments}
 */
export default function PostCard({ post }) {
  const {
    user: { name, profilePicture },
    media,
    text,
    likes,
    dislikes,
    comments,
  } = post;
  // Handlers for button clicks
  const handleLike = () => {};
  const handleDislike = () => {};
  const handleComments = () => {};

  return (
    <div className="grid grid-cols-4 grid-rows-4 border rounded-lg overflow-hidden shadow-md font-montserrat">
      {/* Media */}
      <div className="col-span-3 row-span-3 h-full relative overflow-hidden bg-gray-100">
        {media.length > 1 ? (
          // Handles multiple pieces of media
          <Carousel>
            {media.map((file, index) => (
              <div className="w-full h-full relative">
                <img
                  className="absolute inset-0 w-full h-full object-scale-down"
                  src={`/public/${file}`}
                  alt={`media-1`}
                />
              </div>
            ))}
          </Carousel>
        ) : (
          // Handles 0 or 1 pieces of media
          <div className="w-full h-full relative">
            <img
              className="absolute inset-0 w-full h-full object-scale-down"
              src={
                media.length === 0 ? MediaPlaceholder : `/public/${media[0]}`
              }
              alt={`media-1`}
            />
          </div>
        )}
      </div>

      {/* Ratings */}
      <div className="col-span-1 row-span-3 flex flex-col justify-end bg-gray-100">
        <button
          onClick={handleLike}
          className="flex items-center space-x-2 my-4 hover:text-blue-500"
        >
          <HandThumbUpIcon className="w-10 h-10" />
          <span>{likes}</span>
        </button>
        <button
          onClick={handleDislike}
          className="flex items-center space-x-2 my-4 hover:text-red-500"
        >
          <HandThumbDownIcon className="w-10 h-10" />
          <span>{dislikes}</span>
        </button>
        <button
          onClick={handleComments}
          className="flex items-center space-x-2 my-4 hover:text-green-500"
        >
          <ChatBubbleLeftIcon className="w-10 h-10" />
          <span>{comments}</span>
        </button>
      </div>

      {/* Post Info */}
      <div className="col-span-4 row-span-1 flex items-center p-4 bg-white">
        <img
          src={profilePicture || DefaultProfilePicture}
          alt="Profile"
          className="w-14 h-14 rounded-full"
        />
        <div className="ml-4">
          <h2 className="font-bold text-lg">{name}</h2>
          <p className="text-gray-700">{text}</p>
        </div>
      </div>
    </div>
  );
}
