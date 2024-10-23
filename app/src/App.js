import React from "react";
import { Route, Routes } from "react-router-dom";
import Home from "./pages/Home.js";
import Products from "./pages/Products.js";
import Contact from "./pages/Contact.js";
import About from "./pages/About.js";
import Login from "./pages/Login.js";
import SignUp from "./pages/SignUp.js";
import MainNav from "./components/MainNav.js";
import Main from "./pages/Main.js";
import SimpleBody from "./components/SimpleBody.js";
import ProfilePage from "./pages/ProfilePage/ProfilePage.js";
import MainPage from "./pages/MainPage/MainPage.js";
import MessagePage from "./pages/MessagePage/Messages.js";
import DiscoverPage from "./pages/DiscoverPage/DiscoverPage.js";

function App() {
  //let component;

  return (
    
    <div className="App">

      <Routes>
        <Route path="/" element={<Home />}>
          <Route path="" element={<SimpleBody />} />
        </Route>
        <Route path="/home" element={<Home />}>
          <Route path="products" element={<Products />} />
          <Route path="about" element={<About />} />
          <Route path="contact" element={<Contact />} />
          <Route path="" element={<SimpleBody />} />
        </Route>
        <Route path="/snippet" element={<MainPage />} />
        {/* Temporary route to profile page, to be officially implemented */}
        <Route path="/profilepage" element={<ProfilePage />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/messages" element={<MessagePage />} />
        <Route path="/discover" element={<DiscoverPage />} />
      </Routes>
    </div>
  );
}

export default App;
