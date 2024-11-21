import React from "react";
import PostCard from "../MainPage/PostCard";

export default function ProfileFeed({ posts, error }) {
  if (error) {
    return <p className="text-red-500">{error}</p>;
  }

  if (posts.length === 0) {
    return <p>No posts available</p>;
  }

  return (
    <div>
      <h1>Feed</h1>
      {posts.map(
        (post) => (
          console.log("post", post),
          (
            <div key={post.id} className="py-3">
              <PostCard
                post={{
                  id: post.id,
                  user: {
                    name: post.user?.name || "Unknown",
                    profilePicture: post.user?.profilePicture || null,
                  },
                  media: post.images || [], //Defaults to empty array in case post.images is undefined
                  text: post.text,
                  likes: post.totalLikes || 0, //Defaults to 0 likes if none are found
                  dislikes: post.totalDislikes || 0, //Defaults to 0 dislikes if none are found
                }}
                // loadPosts={loadPosts}
              />
            </div>
          )
        )
      )}
    </div>
  );
}
