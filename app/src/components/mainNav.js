// navbar.js
import React from 'react';
//import Navbar from 'react-bootstrap/Navbar';
//import Container from 'react-bootstrap/Container';
//import Nav from 'react-bootstrap/Nav';
import './Navbar.css'; // Create a CSS file for styling

const MainNav = () => {
  return (
    /*
    <Navbar expands="lg" className="bg-body-tertiary" class="navbar">
        <Container>
            <Navbar.Brand href="#home" className="flex items-center, text-white" class="navbar-logo">
                <img 
                src="https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Microsoft_logo.svg/2048px-Microsoft_logo.svg.png"
                className="mr-2 w-5 h-10"
                
                />
                <h1 className="text-4xl font-bold self-center mx-2">Macrosoft ltd.</h1>
            </Navbar.Brand>
        </Container>
    </Navbar>
    */
    /*
    <Navbar expand="lg" className="bg-body-tertiary">
      <Container>
        <Navbar.Brand href="#home">
        <img
            src="https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Microsoft_logo.svg/2048px-Microsoft_logo.svg.png"
            width="5%"
            
        />
          Macrosoft ltd.</Navbar.Brand>
        <Nav className="me-auto">
          <Nav.Link href="#home">Home</Nav.Link>
          <Nav.Link href="#products">Products</Nav.Link>
          <Nav.Link href="#about">About</Nav.Link>
          <Nav.Link href="#contact">Contact</Nav.Link>


        </Nav>
      </Container>
    </Navbar>
    */
   
    <nav className="navbar">
      
      <a href="/">
      <div className="navbar-logo" >
        <img
            src="https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Microsoft_logo.svg/2048px-Microsoft_logo.svg.png"
            width="5%"
            
        />
        <h1 className="text-4xl font-bold">Macrosoft ltd.</h1>
      </div>
      </a>
      <ul className="navbar-links">
        <li><a href="#home">Home</a></li>
        <li><a href="#products">Products</a></li>
        <li><a href="#about">About</a></li>
        <li><a href="#contact">Contact</a></li>
      </ul>
    </nav>
  );
};

export default MainNav;