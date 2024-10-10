import React, { useState } from 'react';
import { Modal, Button, Carousel, Form } from 'react-bootstrap';

const PostCreator = ({ show, handleClose }) => {
  const [mediaFiles, setMediaFiles] = useState([]);

  const handleMediaChange = (event) => {
    const files = Array.from(event.target.files);
    setMediaFiles(files);
  };

  return (
    <Modal show={show} onHide={handleClose} centered backdrop="true">
      <Modal.Body className="d-flex flex-column justify-content-center align-items-center">
        {mediaFiles.length === 0 ? (
          <Button
            variant="primary"
            onClick={() => document.getElementById('mediaInput').click()}
            className="mb-4"
          >
            Select Images/Videos
          </Button>
        ) : (
          <Carousel className="w-100 mb-4">
            {mediaFiles.map((file, index) => (
              <Carousel.Item key={index}>
                {file.type.startsWith('video') ? (
                  <video
                    className="d-block w-100"
                    controls
                    src={URL.createObjectURL(file)}
                  />
                ) : (
                  <img
                    className="d-block w-100"
                    src={URL.createObjectURL(file)}
                    alt={`media-${index}`}
                  />
                )}
              </Carousel.Item>
            ))}
          </Carousel>
        )}
        <Form.Control
          type="file"
          id="mediaInput"
          onChange={handleMediaChange}
          accept="image/*,video/*"
          multiple
          style={{ display: 'none' }}
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