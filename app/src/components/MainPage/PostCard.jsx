import React, { useState } from "react";
import { Carousel } from "react-bootstrap";
import { likePost, dislikePost, unlikePost } from "../../api/PostAPI"
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
export default function PostCard({ post, loadPosts }) {
  const {
    id,
    user: { name, profilePicture },
    media,
    text,
    likes,
    dislikes,
    comments,
  } = post;
  
  const [liked, setLiked] = useState(false); //Track if post is liked by user
  const [disliked, setDisliked] = useState(false); //Track if post is disliked by user

  //Handles liking post and unliking post if like button is clicked while the user has already liked the post
  const handleLike = async () => {
    try {
      const token = localStorage.getItem("authToken");
      if (liked) {
        await unlikePost(id, token); 
      } else if (disliked) {
        await unlikePost(id, token);
        setDisliked((prevDisliked) => !prevDisliked);
        await likePost(id, token);
      } else {
        await likePost(id, token);
      }
      loadPosts();
      setLiked((prevLiked) => !prevLiked); //toggles liked state 
    } catch (error) {
      console.error("Error liking/unliking post: ", error);
    }
  };

  const handleDislike = async () => {
    try {
      const token = localStorage.getItem("authToken");
      if (liked) {
        await unlikePost(id, token)
        setLiked((prevLiked) => !prevLiked); //toggle liked state 
      } else if (disliked) {    //If already disliked, toggle disliked state to false and delete dislike from database 
        await unlikePost(id, token);
        setDisliked((prevDisliked) => !prevDisliked);
        loadPosts();
        return;
      }
      await dislikePost(id, token);
      setDisliked((prevDisliked) => !prevDisliked);
      loadPosts();
    } catch (error) {
      console.error("Error disliking post: ", error);
    }
  };

  const handleComments = async () => {};

  return (
    <div className="h-[28rem] grid grid-cols-4 grid-rows-4 border rounded-lg overflow-hidden shadow-md font-montserrat">
      {/* Media */}
      <div className="col-span-3 row-span-3 h-full relative overflow-hidden bg-gray-100">
        {media.length > 1 ? (
          //Handles multiple pieces of media
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
          //Handles 0 or 1 pieces of media
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
