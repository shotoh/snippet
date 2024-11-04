import React, { useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";

const EditableProfileModal = ({ show, onClose, image, displayName, biography, onSubmit }) => {
  const [currentImage, setCurrentImage] = useState(
    typeof image === "string" ? { src: image, alt: "Profile" } : image
  );
  const [imageData, setImageData] = useState("");
  const [name, setName] = useState(displayName);
  const [bio, setBio] = useState(biography);

  const handleImageClick = () => {
    const fileInput = document.createElement("input");
    fileInput.type = "file";
    fileInput.accept = "image/*"; // Accept only image files
  
    fileInput.onchange = (event) => {
      const file = event.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onloadend = () => {
          const base64Image = reader.result;
          if(typeof image === "string") {
            setImageData(base64Image);
            setCurrentImage(base64Image);
            console.log("yipee");
          } else {
            setCurrentImage({src: base64Image, alt: "Updated Profile Image"}); // Set the base64 image directly

          }
          console.log(currentImage);
        };
        reader.readAsDataURL(file); // Reads as base64 Data URL
      }
    };
  
    fileInput.click(); // Trigger the file input click
  };

  // Handler for form submission
  const handleSubmit = () => {
    console.log(name + "\n" + bio + "\n" + imageData);
    onSubmit({ image: imageData, displayName: name, biography: bio }); 
    onClose();
  };

  return (
    <Modal show={show} onHide={onClose} backdrop="true" centered>
      <Modal.Header closeButton>
        <Modal.Title>Edit Profile</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {/* Replaceable image */}
        <div className="text-center mb-3">
          <img
            src={currentImage}
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
        {/* Form */}
        <Form>
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