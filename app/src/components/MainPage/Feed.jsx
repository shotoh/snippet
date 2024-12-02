import React from "react";
import PostCard from "./PostCard";
import DefaultProfile from "../../images/defaultprofile.png";

export default function Feed({ posts, error, loadPosts }) {
  if (error) {
    return <p className="text-red-500">{error}</p>;
  }

  if (posts.length === 0) {
    return <p>No posts available</p>;
  }

  return (
    <div>
      {posts.map((post) => (
        <div key={post.id} className="py-3">
          <PostCard
            post={{
              id: post.id,
              user: {
                userID: post.user?.id || null,
                name: post.user?.username || "Unknown user",
                profilePicture: post.user?.profilePicture,
              },
              media: post.images || [], //Defaults to empty array in case post.images is undefined
              text: post.content,
              likes: post.totalLikes || 0, //Defaults to 0 likes if none are found
              dislikes: post.totalDislikes || 0, //Defaults to 0 dislikes if none are found
              likeState: post.liked, // -1 = Disliked, 0 = Neither, 1 = Liked
              comments: post.comments || [], //Defaults to empty array if post.comments is undefined
            }}
            loadPosts={loadPosts}
          />
        </div>
      ))}
    </div>
  );
}
