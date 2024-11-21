import React from "react";
import TrendingCard from "./TrendingCard";

export default function TrendingBar({ posts, error }) {
  if (error) {
    return <p className="text-red-500">{error}</p>;
  }

  if (!posts || posts.length === 0) {
    return <p className="text-gray-500">No trending posts available.</p>;
  }

  console.log("Trending posts: ", posts);

  return (
    <div className="w-full h-full px-3">
      <div className="text-center py-4 font-montserrat leading-tight text-black border-b border-gray-400">
        <span className="text-2xl">See what's</span> <br />
        <span className="text-5xl font-bold">Trending</span>
      </div>
      <div id="TrendingContentCreator">
        {posts.map((post) => (
          <TrendingCard key={post.id} post={post} />
        ))}
      </div>
    </div>
  );
}