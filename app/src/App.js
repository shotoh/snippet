import React from "react";
import {Route, Routes } from "react-router-dom";
import Home from "./pages/Home.js";
import Products from "./pages/Products.js";
import Contact from "./pages/Contact.js";
import About from "./pages/About.js";
import MainNav from "./components/MainNav.js";
import Main from "./pages/Main.js";
import SimpleBody from "./components/SimpleBody.js";

function App() {
  //let component;



  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Home />}>
          <Route path="" element={<SimpleBody /> } />
        </Route>
        <Route path="/home" element={<Home />} >
            <Route path="products" element={<Products />} />
            <Route path="about" element={<About />} />
            <Route path="contact" element={<Contact />} />
            <Route path="" element={<SimpleBody /> } />
        </Route>
        <Route path="/snipit" element={<Main />} />
      </Routes>
      
    </div>
  );
}

export default App;
