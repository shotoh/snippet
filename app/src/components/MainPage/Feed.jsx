import React, { useState, useEffect } from "react";

export default function Feed({ posts: userPosts = [] }) {
  const [dbPosts, setDBPosts] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchPosts = async () => {
      const token = localStorage.getItem('authToken');
      if (!token) {
        setError('User is not authenticated');
        return;
      }

      try {
        const response = await fetch('/api/posts', {
          method: 'GET',
          headers: {
            'Authorization': 'Bearer ${token}',
          },
        });

        const result = await response.json();
        if (response.ok && result.status === 'success') {
          setDBPosts(result.data);
        } else {
          setError('Error fetching posts');
        }
      } catch (err) {
        setError('Error fetching posts');
        console.error('Error fetching posts:', err);
      }
    };
    fetchPosts();
  }, []);

  const combinedPosts = [...userPosts, ...dbPosts];

  return (
    <div className="feed-container">
      <h1>Feed</h1>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {combinedPosts.length === 0 ? (
        <p>No posts available</p>
      ) : (
        combinedPosts.map((post) => (
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
              {/* only displays valid timestamps. Otherwise it adds extra text to the post and i dont think we want that */}
            {post.timestamp && !isNaN(new Date(post.timestamp).getTime()) && (
              <small>{new Date(post.timestamp).toLocaleString()}</small>
            )}
          </div>
        ))
      )}
    </div>
  );
}