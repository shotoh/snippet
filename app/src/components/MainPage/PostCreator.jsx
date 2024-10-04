import React, { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';

const PostCreator = ({ show, handleClose, onPostCreate }) => {
  const [postContent, setPostContent] = useState('');
  const [postTitle, setPostTitle] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleCreatePost = async () => {
    if (!postContent || !postTitle) {
      setError('Title and text are required');
      return;
    }

    setLoading(true);
    setError('');

    const token = localStorage.getItem('authToken');
    if (!token) {
      setError('User is not authenticated');
      setLoading(false);
      return;
    }

    const userId = parseJwt(token).sub; 

    try {
      const response = await fetch('/api/posts', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify({
          title: postTitle,
          content: postContent,
          userId: userId,  
        }),
      });

      const result = await response.json();
      if (response.ok && result.status === 'success') {
        onPostCreate(result.data); 
        handleClose();
      } else {
        setError('Error creating post');
      }
    } catch (err) {
      setError('Error creating post');
    }
    setLoading(false);
  };

  const parseJwt = (token) => {
    try {
      const base64Url = token.split('.')[1];
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      return JSON.parse(window.atob(base64));
    } catch (error) {
      return null;
    }
  };

  return (
    <Modal
      show={show}
      onHide={handleClose}
      centered
      backdrop="static"
    >
      <Modal.Body className="d-flex flex-column justify-content-center align-items-center">
        <Form.Control
          type="text"
          placeholder="Post Title"
          className="w-100 mb-3"
          value={postTitle}
          onChange={(e) => setPostTitle(e.target.value)}
        />
        <Form.Control
          as="textarea"
          rows={4}
          placeholder="Write post content here"
          className="w-100 mb-3"
          value={postContent}
          onChange={(e) => setPostContent(e.target.value)}
        />
        {error && <p style={{ color: 'red' }}>{error}</p>}
        <Button variant="success" onClick={handleCreatePost} className="align-self-end" disabled={loading}>
          {loading ? 'Creating...' : 'Create'}
        </Button>
      </Modal.Body>
    </Modal>
  );
};

export default PostCreator;