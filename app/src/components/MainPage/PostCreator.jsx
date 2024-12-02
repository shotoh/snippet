import React, { useState } from "react";
import { Modal, Button, Carousel, Form } from "react-bootstrap";

import { uploadPostImage } from "../../api/ImageAPI";

const PostCreator = ({ show, handleClose, onPostCreate }) => {
  const [postContent, setPostContent] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [mediaFiles, setMediaFiles] = useState([]);

  const handleMediaChange = (event) => {
    const files = Array.from(event.target.files);
    setMediaFiles(files);
    
  };

  const handleCreatePost = async () => {
    if (!postContent) {
      setError("Text content is required");
      return;
    }

    setLoading(true);
    setError("");

    const token = localStorage.getItem("authToken");
    if (!token) {
      setError("User is not authenticated");
      setLoading(false);
      return;
    }

    const userId = parseJwt(token).sub;

    try {
      // Log the outgoing request details
      console.log("Attempting to create a post with the following data:", {
        content: postContent,
        userId: userId,
      });

      const response = await fetch("/api/posts", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          content: postContent,
          userId: userId,
        }),
      });

      // Log the response status and body
      console.log("Response Status:", response.status);
      const result = await response.json();
      console.log("Response Body:", result);

      if (response.ok && result.status === "success") {
        const postId = result.data.id; // Get the postId from the response
        const userId = result.data.user.id;

        // Now upload the image if there is one
        console.log(mediaFiles.length, "media files to upload");
        if (mediaFiles.length > 0) {
          mediaFiles.map(async (file) => {
            try {
              console.log(file);
              await uploadPostImage(file, postId, token); // Pass the arguments in the correct order
            } catch (error) {
              // Handle error in image upload
              setError(
                `Post created but failed to upload image: ${error.message}`
              );
              console.error("Error uploading image:", error);
            }
          });
        }

        onPostCreate(); // Refresh posts in the parent component
        handleClose(); // Close the modal after successful post creation
        window.location.reload();
      } else {
        // Log error message and display it to the user
        setError(result.message || `Error creating post: ${response.status}`);
        console.error("Error creating post:", result);
      }
    } catch (err) {
      // Log and show detailed error message
      setError(`Error creating post: ${err.message}`);
      console.error("Error in post creation request:", err);
    }

    setLoading(false);
  };

  const parseJwt = (token) => {
    try {
      const base64Url = token.split(".")[1];
      const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
      return JSON.parse(window.atob(base64));
    } catch (error) {
      return null;
    }
  };

  return (
    <Modal show={show} onHide={handleClose} centered backdrop="true">
      <Modal.Body className="d-flex flex-column justify-content-center align-items-center">
        {mediaFiles.length === 0 ? (
          <Button
            variant="primary"
            onClick={() => document.getElementById("mediaInput").click()}
            className="mb-4"
          >
            Select Images/Videos
          </Button>
        ) : (
          <Carousel className="w-100 mb-4">
            {mediaFiles.map((file, index) => (
              <Carousel.Item key={index}>
                {file.type.startsWith("video") ? (
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
          style={{ display: "none" }}
        />
        <Form.Control
          as="textarea"
          rows={4}
          placeholder="Write post content here"
          className="w-100 mb-3"
          value={postContent}
          onChange={(e) => setPostContent(e.target.value)}
        />
        {error && <p style={{ color: "red" }}>{error}</p>}
        <Button
          variant="success"
          onClick={handleCreatePost}
          className="align-self-end"
          disabled={loading}
        >
          {loading ? "Creating..." : "Create"}
        </Button>
      </Modal.Body>
    </Modal>
  );
};

export default PostCreator;
