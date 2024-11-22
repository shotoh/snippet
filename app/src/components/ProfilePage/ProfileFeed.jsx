import React from "react";
import PostCard from "../MainPage/PostCard";

export default function ProfileFeed({ posts, error, loadPosts }) {
  if (error) {
    return <p className="text-red-500">{error}</p>;
  }

  if (posts.length === 0) {
    return <p>No posts available</p>;
  }

  return (
    <div>
      <h1>Feed</h1>
      {posts.map((post) => (
        <div key={post.id} className="py-3">
          <PostCard
            post={{
              id: post.id,
              user: {
                name: post.user?.username || "Unknown",
                profilePicture: post.user?.profilePicture || null,
              },
              media: post.images || [], //Defaults to empty array in case post.images is undefined
              text: post.content,
              likes: post.totalLikes || 0, //Defaults to 0 likes if none are found
              dislikes: post.totalDislikes || 0, //Defaults to 0 dislikes if none are found
              likedState: post.liked, // -1 = Disliked, 0 = Neither, 1 = Liked
            }}
            loadPosts={loadPosts}
          />
        </div>
      ))}
    </div>
  );
}
