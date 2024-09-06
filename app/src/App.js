import React from "react";
//import {Route, Routes } from "react-router-dom";
import Home from "./pages/Home.js";
import MainNav from "./components/mainNav.js";
import SocialMedia from "./pages/SocialMedia.js";
import SimpleBody from "./components/SimpleBody.js";

function App() {
  let component;
  
  switch(window.location.pathname) {
    case "/":
    case "/home":
      component = <Home />
      break;
      case "/socialmedia":
      component = <SocialMedia/>
      break;

  }
  
  return (
    <div className="App">
      {component}
    </div>
  );
}

export default App;
