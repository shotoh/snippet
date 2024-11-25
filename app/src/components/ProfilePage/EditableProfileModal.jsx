import React, { useState } from "react";
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
  const handleSubmit = () => {
    console.log(name + "\n" + bio + "\n" + selectedFile);
    onSubmit({ image: selectedFile, displayName: name, biography: bio });
    onClose();
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
                  currentImage.includes("defaultprofile2")
                    ? currentImage
                    : `/public/${currentImage}`
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
