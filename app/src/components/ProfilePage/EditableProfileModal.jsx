import React, { useState, useEffect } from "react";
import { Modal, Button, Form } from "react-bootstrap";

const EditableProfileModal = ({
  show,
  onClose,
  image,
  username,
  biography,
  onSubmit,
}) => {
  const [currentImage, setCurrentImage] = useState(image);
  const [selectedFile, setSelectedFile] = useState(null); // Store the File object here
  const [name, setName] = useState(username || "");
  const [bio, setBio] = useState(biography || "");

  useEffect(() => {
    console.log(image);
    console.log(currentImage);
  }, []);
  
  const handleImageClick = () => {
    document.getElementById("mediaInput").click(); // Trigger file input click
  };

  const handleImageChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      setSelectedFile(file); // Store the file object in selectedFile
      console.log(file);
      const reader = new FileReader();
      reader.onloadend = () => {
        setCurrentImage(reader.result); // Update currentImage with base64 data
      };
      reader.readAsDataURL(file);
    }
  };

  // Handler for form submission
  const handleSubmit = async () => {
    console.log(name + "\n" + bio + "\n" + selectedFile);
    await onSubmit({ image: selectedFile, displayName: name, biography: bio });
    await onClose();
    window.location.reload();
  };

  return (
    <Modal show={show} onHide={onClose} backdrop="true" centered>
      <Modal.Header closeButton>
        <Modal.Title>Edit Profile</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {/* Form */}
        <Form>
          {/* Replaceable image */}
          <Form.Group>
            <div className="text-center mb-3">
            <img
                src={
                  currentImage.startsWith("data:image")
                    ? currentImage // If it's a base64 preview
                    : currentImage.includes("static")
                    ? currentImage :
                    `/public/${currentImage}` // Otherwise use the server-side path
                }
                alt="Profile"
                className="mx-auto"
                onClick={handleImageClick}
                style={{
                  width: "100px",
                  height: "100px",
                  borderRadius: "50%",
                  objectFit: "cover",
                  cursor: "pointer",
                }}
              />
            </div>
            <Form.Control
              type="file"
              id="mediaInput"
              onChange={handleImageChange}
              accept="image/*"
              style={{ display: "none" }}
            />
          </Form.Group>

          <Form.Group controlId="formDisplayName">
            <Form.Label>Display Name</Form.Label>
            <Form.Control
              type="text"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </Form.Group>
          <Form.Group controlId="formBiography" className="mt-3">
            <Form.Label>Biography</Form.Label>
            <Form.Control
              as="textarea"
              rows={3}
              value={bio}
              onChange={(e) => setBio(e.target.value)}
            />
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="primary" onClick={handleSubmit}>
          Submit
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default EditableProfileModal;
