import React, { useState, useEffect } from "react";
import { Carousel } from "react-bootstrap";
import { likePost, dislikePost, unlikePost } from "../../api/PostAPI";
import { FaThumbsUp, FaThumbsDown, FaComments } from "react-icons/fa";
import Comments from "../MainPage/Comments";

import DefaultProfilePicture from "../../images/defaultprofile2.jpg";
import MediaPlaceholder from "../../images/nomedia.jpg";

export default function DiscoverCard({ discoverPost, loadDiscoverPosts }) {
  const {
    id,
    user: { userID, name, profilePicture },
    media,
    text,
    likes,
    dislikes,
    likedState,
  } = discoverPost;

  const [liked, setLiked] = useState(false);
  const [disliked, setDisliked] = useState(false);
  const [showComments, setShowComments] = useState(false);
  const [commentCount, setCommentCount] = useState(0);

  useEffect(() => {
    const fetchCommentCount = async () => {
      try {
        const response = await fetch(`/api/comments?post=${id}`, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("authToken")}`,
          },
        });
        const result = await response.json();
        if (response.ok && result.status === "success") {
          setCommentCount(result.data.length);
        }
      } catch (error) {
        console.error("Failed to fetch comment count:", error);
      }
    };

    fetchCommentCount();
    handleLikedState();
  }, [id]);

  const handleLikedState = () => {
    if (likedState === 1) {
      setLiked(true);
    } else if (likedState === -1) {
      setDisliked(true);
    }
  };

  const handleLike = async () => {
    try {
      const token = localStorage.getItem("authToken");
      if (liked) {
        await unlikePost(id, token);
      } else if (disliked) {
        await unlikePost(id, token);
        setDisliked(false);
        await likePost(id, token);
      } else {
        await likePost(id, token);
      }
      loadDiscoverPosts();
      setLiked(!liked);
    } catch (error) {
      console.error("Error liking/unliking post:", error);
    }
  };

  const handleDislike = async () => {
    try {
      const token = localStorage.getItem("authToken");
      if (liked) {
        await unlikePost(id, token);
        setLiked(false);
      } else if (disliked) {
        await unlikePost(id, token);
        setDisliked(false);
        loadDiscoverPosts();
        return;
      }
      await dislikePost(id, token);
      setDisliked(!disliked);
      loadDiscoverPosts();
    } catch (error) {
      console.error("Error disliking post: ", error);
    }
  };

  const profileURL = `/snippet/user/${userID}`;

  return (
    <div className="grid grid-cols-4 grid-rows-[21rem_1fr] border rounded-lg overflow-hidden shadow-md font-montserrat">
      <div className="h-[21rem] col-span-4 grid grid-cols-4 grid-rows-1">
        {/* Media */}
        <div className="col-span-3 h-full relative overflow-hidden bg-gray-100">
          {media.length > 1 ? (
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
            <div className="w-full h-full relative">
              <img
                className="absolute inset-0 w-full h-full object-scale-down"
                src={
                  media.length === 0 ? MediaPlaceholder : `/public/${media[0]}`
                }
                alt="media"
              />
            </div>
          )}
        </div>

        {/* Ratings */}
        <div className="pl-3 col-span-1 h-full flex flex-col justify-end bg-white">
          <button
            onClick={handleLike}
            className={`flex items-center space-x-2 my-4 hover:text-primaryLight ${
              liked ? "text-primaryLight" : ""
            }`}
          >
            <FaThumbsUp className="w-6 h-6" />
            <span>{likes}</span>
          </button>
          <button
            onClick={handleDislike}
            className={`flex items-center space-x-2 my-4 hover:text-secondaryLight ${
              disliked ? "text-secondaryLight" : ""
            }`}
          >
            <FaThumbsDown className="w-6 h-6" />
            <span>{dislikes}</span>
          </button>
          <button
            onClick={() => setShowComments(true)}
            className="flex items-center space-x-2 my-4 hover:text-primaryLight"
          >
            <FaComments className="w-6 h-6" />
            <span>{commentCount}</span>
          </button>
        </div>
      </div>

      {/* Post Info */}
      <div className="col-span-4 flex items-start p-4 bg-white border-1">
        <a href={profileURL}>
          <img
            src={
              profilePicture
                ? `/public/${profilePicture}`
                : DefaultProfilePicture
            }
            alt="Profile"
            className="w-20 h-20 rounded-full object-cover cursor-pointer"
          />
        </a>
        <div className="ml-4 flex-1">
          <a href={profileURL} className="no-underline text-black">
            <h2 className="font-bold text-lg cursor-pointer inline">{name}</h2>
          </a>
          <div>
            <div className={`text-gray-700`}>{text}</div>
          </div>
        </div>
      </div>

      {/* Comments */}
      {showComments && (
        <Comments
          postId={id}
          onClose={() => setShowComments(false)}
          updateCommentCount={setCommentCount}
        />
      )}
    </div>
  );
}