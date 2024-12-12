import React, { useEffect, useState } from "react";
import { Navbar } from "react-bootstrap";
import Dropdown from "react-bootstrap/Dropdown";
import NavDropdown from "react-bootstrap/NavDropdown";
import NavLink from "react-bootstrap/NavLink";
import "./xtra.css";
import PostCreator from "./PostCreator";
import SettingsPopup from "./SettingsPopup";
import DefaultProfile from "../../images/defaultprofile.png";


export default function NavBar({ onPostCreated }) {
  const [showModal, setShowModal] = useState(false);
  const handleOpen = () => setShowModal(true);
  const handleClose = () => setShowModal(false);
  const [username, setUsername] = useState("");
  const [userId, setUserId] = useState();
  const [profilePicture, setProfilePicture] = useState("");

  const [showSettings, setShowSettings] = useState(false);
  const handleShowSettings = () => setShowSettings(true);
  const handleCloseSettings = () => setShowSettings(false);

  const handlePostCreate = (newPost) => {
    onPostCreated(newPost);
    setShowModal(false);
  };

  const handleLogout = () => {
    localStorage.removeItem("authToken");
    window.location.href = "/login";
  }

  // Helper function to parse JWT token
  const parseJwt = (token) => {
    try {
      const base64Url = token.split(".")[1];
      const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
      return JSON.parse(window.atob(base64));
    } catch (error) {
      return null;
    }
  };

  const getUsername = async () => {
    //WIP
    const token = localStorage.getItem("authToken");
    try {
      //Find Friend
      let url = `/api/users`;

      let response = await fetch(url, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });

      if (!response.ok) {
        throw new Error(`Failed to fetch users: ${response.statusText}`);
      }
      const users = await response.json();
      const userData = users.data;

      const userIdFromToken = parseInt(parseJwt(token).sub);
      // Find the user by the specific username
      const foundUser = userData.find((user) => user.id === userIdFromToken);


      if (foundUser) {
        console.log(foundUser);
        setUsername(foundUser.username);
        setUserId(foundUser.id);

        if(typeof(foundUser.profilePicture) === "string") {
        setProfilePicture(foundUser.profilePicture);

        } else {
          console.log("PROFILE PIC IS NOT AN OBJECT, PLEASE PANIC");
        }

      } else {
        console.log("User not found");
        return "User Not Found";
      }
    } catch (err) {
      console.error("error getting username:", err);
    }
  };


  useEffect(() => {
    getUsername();
  }, []);

  function UserProfile({profilePicture}) {
    return (
      <Navbar.Collapse className="justify-end mr-8">
        <NavDropdown
          title={
            <div 
            data-testid="nav-dropdown" className="flex items-center py-1.5 px-4 rounded-lg cursor-pointer hover:backdrop-brightness-90 transition ease-in-out">
              <Navbar.Text className="text-white font-montserrat text-center mr-4 leading-tight">
                {username ? (
                  <div>
                  <span>Welcome back,</span> <br />
                  <span>{username}!</span>
                  </div>
                ) : (
                  <div>
                  <span>Hello Guest!</span>
                  </div>
                )}
              </Navbar.Text>
              <img
                src={profilePicture != "" ? ("/public/" + String(profilePicture)) : DefaultProfile}
                
                width="55"
                height="55"
                className="border-2 border-gray-900 rounded-full bg-white w-14 h-14 object-cover"
                alt="User profile"
              />
            </div>
          }
          id="nav-dropdown"
          className="no-caret"
        >
          <NavDropdown.Item as="button" onClick={handleOpen}>
            Create Post
          </NavDropdown.Item>
          <NavDropdown.Item href={`/snippet/user/${userId}`}>
          Profile
          </NavDropdown.Item>
          <NavDropdown.Item as="button" onClick={handleShowSettings}> 
            Settings 
          </NavDropdown.Item>   
          <NavDropdown.Divider />
          <NavDropdown.Item href="/login" onClick={handleLogout}>
          {username ? ("Logout") : ("Sign In")}
          </NavDropdown.Item>
        </NavDropdown>
      </Navbar.Collapse>
    );
  }

  return (
    <div>
      <Navbar className="flex min-w-full h-20 !bg-primaryLight border-b-4 border-secondaryLight">
        <WebsiteLogo />
        <NavButtons />
        <UserProfile profilePicture={profilePicture} />
      </Navbar>
      <PostCreator
        show={showModal}
        handleClose={handleClose}
        onPostCreate={handlePostCreate}
      />
      <SettingsPopup
      show={showSettings}
      handleClose={handleCloseSettings}
      />
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
        <a href="/discover" className={buttonStyle}>
          Discover
        </a>
      </Navbar.Text>
      <Navbar.Text>
        <a href="/messages" className={buttonStyle}>
          Messages
        </a>
      </Navbar.Text>
    </Navbar.Collapse>
  );
}
