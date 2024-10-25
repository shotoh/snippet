import React from "react";

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
          <h3 className="font-bold">{post.title}</h3>
          <p>{post.content}</p>
        </div>
      ))}
    </div>
  );
}