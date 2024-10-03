import React from "react";
import { Navbar } from "react-bootstrap";
import Dropdown from 'react-bootstrap/Dropdown';
import NavDropdown from 'react-bootstrap/NavDropdown';
import NavLink from "react-bootstrap/NavLink";
import "./xtra.css";
import { useState } from 'react';
import { Modal, Button, Carousel, Form } from 'react-bootstrap';

export default function NavBar(props) {
  const [showModal, setShowModal] = useState(false);
  const [mediaFiles, setMediaFiles] = useState([]);

  const [error, setError] = useState(null);
  

  
  const CreatePost = () => {
    
    //Remove '|| true' for forced media posts
    if(mediaFiles.length !== 0 || true) {
      setShowModal(false);

      //Things to grab here
      console.log(mediaFiles.length);
      console.log(document.getElementById("postText").value);
    } else {
      setError("Need at least one image or video to post");
    }
    
    
  };

  const handleOpen = () => {
    setShowModal(true);
    setMediaFiles([]);
  }

  const handleMediaChange = (event) => {
    const files = Array.from(event.target.files);
    const allowedTypes = ['image/png', 'image/jpeg', 'image/gif', 'video/mp4'];
    const filteredFiles = Array.from(event.target.files).filter(file =>
      allowedTypes.includes(file.type)
    );
    if(files.length > filteredFiles.length) {
      setError("One or more files are in an unaccepted format");
    } else {
      setError(null);
    }
    setMediaFiles(filteredFiles);
  };

  const handleClose = () => {
    setShowModal(false);
    setMediaFiles([]);
  };
  
  function UserProfile({ username = "User" }) {
  
    return (
      
      
  
      <Navbar.Collapse className="justify-end mr-8">
    <NavDropdown
      title={
        <div className="flex items-center py-1.5 px-4 rounded-lg cursor-pointer hover:backdrop-brightness-90 transition ease-in-out">
          <Navbar.Text className="text-white font-montserrat text-center mr-4 leading-tight">
            <span>Welcome back,</span> <br />
            <span>{username}!</span>
          </Navbar.Text>
          <img
            src={require("../../images/macrosoftLogo.png")}
            width="55"
            height="55"
            className="border-2 border-gray-900 rounded-full bg-white"
            alt="User profile"
          />
        </div>
      }
      id="nav-dropdown"
      className="no-caret"
    >
      
      <NavDropdown.Item as="button" onClick={handleOpen}>Create Post</NavDropdown.Item>
      <NavDropdown.Item href="/profilepage">Profile</NavDropdown.Item>
      <NavDropdown.Item href="/settings">Settings</NavDropdown.Item>
      <NavDropdown.Divider />
      <NavDropdown.Item href="/login">Logout</NavDropdown.Item>
    </NavDropdown>
  </Navbar.Collapse>
    );
  }

  const CreatePostModal = ({ show, handleClose, handleCreate }) => {
    
  
    return (
      
      <Modal
        show={show}
        onHide={handleClose}
        centered
        backdrop={mediaFiles == 0 ? "true" : "static"}
      >
        <Modal.Body className="d-flex flex-column justify-content-center align-items-center">
          {mediaFiles.length !== 0 && (
            
            <Carousel 
            interval={null} controls={mediaFiles.length > 1} indicators={mediaFiles.length > 1} className="w-100 mb-4">
              {mediaFiles.map((file, index) => (
                <Carousel.Item key={index}>
                  {file.type.startsWith('video') ? (
                    <video
                      className="d-block w-auto max-h-80"
                      controls
                      autoplay
                      src={URL.createObjectURL(file)}
                    />
                  ) : (
                    <img
                      className="d-block w-auto h-72 mx-auto"
                      
                      src={URL.createObjectURL(file)}
                      alt={`media-${index}`}
                    />
                  )}
                </Carousel.Item>
              ))}
            </Carousel>
          )}
          <Button
              variant="primary"
              onClick={() => document.getElementById('mediaInput').click()}
              className="mb-4"
            >
              Select Images/Videos
            </Button>
            {error && <p className="text-red-500 text-md">{error}</p>}
          <Form.Control
            type="file"
            id="mediaInput"
            onChange={handleMediaChange}
            accept="image/png, image/jpeg, image/gif, video/mp4"
            multiple
            style={{ display: 'none' }}
          />
          <Form.Control
            as="textarea"
            id="postText"
            rows={3}
            placeholder="Write your post here..."
            className="w-100 mb-3"
          />
          <div className="flex justify-between w-full">
          <Button variant="secondary" onClick={handleClose}>
            Cancel
          </Button>
          <Button variant="success" onClick={handleCreate}>
            Create
          </Button>
          </div>
          
          
        </Modal.Body>
      </Modal>
    );
  };



  return (
    <div>
      <Navbar className="flex min-w-full h-20 !bg-primaryLight border-b-4 border-secondaryLight">
      <WebsiteLogo />
      <NavButtons />
      <UserProfile username={props.username} />
      </Navbar>
      <CreatePostModal show={showModal} handleClose={handleClose} handleCreate={CreatePost}/>
    </div>
    
  );
}

function WebsiteLogo() {
  return (
    <Navbar.Brand href="/snippet" className="ml-8">
      <img
        src={require("../../images/macrosoftLogo.png")}
        width="60"
        height="60"
        alt="Macrosoft Logo"
      />
    </Navbar.Brand>
  );
}

function NavButtons() {
  const buttonStyle =
    "text-white text-xl font-bold font-montserrat no-underline mx-4 transition ease-in-out hover:!text-slate-300";
  return (
    <Navbar.Collapse className="absolute left-1/2 transform -translate-x-1/2 flex justify-center">
      <Navbar.Text>
        <a href="/snippet" className={buttonStyle}>
          Home
        </a>
      </Navbar.Text>
      <Navbar.Text>
        <a href="#discover" className={buttonStyle}>
          Discover
        </a>
      </Navbar.Text>
      <Navbar.Text>
        <a href="#messages" className={buttonStyle}>
          Messages
        </a>
      </Navbar.Text>
    </Navbar.Collapse>
  );
}






