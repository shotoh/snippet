import React from "react";

export default function Feed({ posts, error }) {
  return (
    <div>
      {error && <p style={{ color: "red" }}>{error}</p>}
      {posts.length === 0 ? (
        <p>No posts available</p>
      ) : (
        posts.map((post) => (
          <div key={post.id} className="bg-white p-3 rounded-lg mt-2">
            <p>{post.content}</p>
            {post.timestamp && !isNaN(new Date(post.timestamp).getTime()) && (
              <small>{new Date(post.timestamp).toLocaleString()}</small>
            )}
          </div>
        ))
      )}
    </div>
  );
}
