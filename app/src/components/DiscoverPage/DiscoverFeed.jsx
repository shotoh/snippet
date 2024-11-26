import React from "react";
import DiscoverCard from "./DiscoverCard";
import DefaultProfile from "../../images/defaultprofile.png";

export default function DiscoverFeed({ posts, error, loadDiscoverPosts }) {
  if (error) {
    return <p className="text-red-500">{error}</p>;
  }

  if (posts.length === 0) {
    return <p>No posts available</p>;
  }

  return (
    <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
      {posts.map((post) => (
        <DiscoverCard
          key={post.id}
          discoverPost={{
            id: post.id,
            user: {
              userID: post.user?.id || null,
              name: post.user?.username || "Unknown user",
              profilePicture: post.user?.profilePicture || DefaultProfile,
            },
            media: post.images || [], // Defaults to an empty array if post.images is undefined
            text: post.content || "",
            likes: post.totalLikes || 0,
            dislikes: post.totalDislikes || 0,
            likedState: post.liked,
          }}
          loadDiscoverPosts={loadDiscoverPosts}
        />
      ))}
    </div>
  );
}