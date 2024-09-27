import React from "react";
import { Navbar } from "react-bootstrap";

export default function NavBar() {
  return (
    <Navbar className="flex min-w-full h-20 !bg-primaryLight border-b-4 border-secondaryLight">
      <WebsiteLogo />
      <NavButtons />
      <UserProfile />
    </Navbar>
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

function UserProfile({ username = "User" }) {
  return (
    <Navbar.Collapse className="justify-end mr-8">
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
    </Navbar.Collapse>
  );
}
