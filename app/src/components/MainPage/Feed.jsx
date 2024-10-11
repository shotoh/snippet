import React from "react";

export default function Feed({ posts, error }) {
  return (
    <div className="feed-container">
      <h1>Feed</h1>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {posts.length === 0 ? (
        <p>No posts available</p>
      ) : (
        posts.map((post) => (
          <div 
            key={post.id} 
            className="post-container"
            style={{
              backgroundColor: '#e6f3f7',
              padding: '15px',
              borderRadius: '8px',
              marginBottom: '10px',
            }}
          >
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
