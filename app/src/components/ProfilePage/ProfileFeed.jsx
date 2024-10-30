import React from "react";
import PostCard from "../MainPage/PostCard";

export default function ProfileFeed({ posts, error }) {
  if (error) {
    return <p className="text-red-500">{error}</p>;
  }

  if (posts.length == 0) {
    return <p>No posts available</p>;
  }

  return (
    <div>
      <h1>Feed</h1>
      {posts.map((post) => (
        <div key={post.id} className="border-b-2 border-gray-300 py-4">
          <PostCard
            post={{
              user: post.user, // ** TODO: profilePicture needs to be figured out in the backend, update this accordingly
              media: post.media, // ** TODO: Attribute needs to be included in post object
              text: post.content, // ** Potentially change if post object changes
              likes: post.likes,
              dislikes: post.dislikes,
              comments: post.comments,
            }}
          />
        </div>
      ))}
    </div>
  );
}
