import React, { useState, useRef, useEffect } from "react";
import { Carousel } from "react-bootstrap";
import { FaThumbsUp, FaThumbsDown, FaComments } from "react-icons/fa";

import MediaPlaceholder from "../../images/nomedia.jpg";
import DefaultProfilePicture from "../../images/defaultprofile2.jpg";

/**
 * Template for a post
 * @param post - { user, media, text, likes, dislikes, comments}
 */
export default function PostCard({ post }) {
  const {
    user: { userID, name, profilePicture },
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

  const [expanded, setExpanded] = useState(false);
  const [isClamped, setIsClamped] = useState(false);
  const textRef = useRef(null);

  const handleToggleExpand = () => {
    setExpanded(!expanded);
  };

  useEffect(() => {
    const element = textRef.current;
    if (element) {
      // Apply 'line-clamp-2' class to measure clamping
      const wasExpanded = expanded;
      element.classList.add("line-clamp-2");
      const isTextClamped = element.scrollHeight > element.clientHeight;
      setIsClamped(isTextClamped);
      // Restore expanded state
      if (wasExpanded) {
        element.classList.remove("line-clamp-2");
      }
    }
  }, [text]);

  const profileURL = `/snippet/user/${userID}`;

  return (
    <div className="grid grid-cols-4 grid-rows-[21rem_1fr] border rounded-lg overflow-hidden shadow-md font-montserrat">
      <div className="h-[21rem] col-span-4 grid grid-cols-4 grid-rows-1">
        {/* Media */}
        <div className="col-span-3 h-full relative overflow-hidden bg-gray-100">
          {media.length > 1 ? (
            // Handles multiple pieces of media
            <Carousel
              interval={null}
              slide={false}
              className="flex justify-center items-center w-full h-full overflow-hidden"
            >
              {media.map((file, index) => (
                <Carousel.Item key={index}>
                  <img
                    className="max-h-[21rem] w-full h-full object-scale-down"
                    src={`/public/${file}`}
                    alt={`media-${index}`}
                  />
                </Carousel.Item>
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
                alt="media-1"
              />
            </div>
          )}
        </div>

        {/* Ratings */}
        <div className="ml-3 col-span-1 h-full flex flex-col justify-end">
          <button
            onClick={handleLike}
            className="flex items-center space-x-2 my-4 hover:text-blue-500"
          >
            <FaThumbsUp className="w-6 h-6" />
            <span>{likes}</span>
          </button>
          <button
            onClick={handleDislike}
            className="flex items-center space-x-2 my-4 hover:text-red-500"
          >
            <FaThumbsDown className="w-6 h-6" />
            <span>{dislikes}</span>
          </button>
          <button
            onClick={handleComments}
            className="flex items-center space-x-2 my-4 hover:text-green-500"
          >
            <FaComments className="w-6 h-6" />
            <span>{comments.length}</span>
          </button>
        </div>
      </div>

      {/* Post Info */}
      <div className="col-span-4 flex items-start p-4 bg-white">
        <a href={profileURL}>
          <img
            src={profilePicture || DefaultProfilePicture}
            alt="Profile"
            className="w-20 h-20 rounded-full object-cover cursor-pointer"
          />
        </a>
        <div className="ml-4 flex-1">
          <a href={profileURL} className="no-underline text-black">
            <h2 className="font-bold text-lg cursor-pointer">{name}</h2>
          </a>
          <div>
            <div
              ref={textRef}
              className={`text-gray-700 ${
                expanded ? "line-clamp-none" : "line-clamp-2"
              } ${isClamped ? "cursor-pointer" : ""}`}
              onClick={isClamped ? handleToggleExpand : undefined}
            >
              {text}
            </div>
            {isClamped && expanded && (
              <span
                className="text-blue-400 text-sm cursor-pointer"
                onClick={handleToggleExpand}
              >
                Show less
              </span>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
