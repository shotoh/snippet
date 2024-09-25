import React, { useState } from 'react';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import { NavLink} from "react-router-dom";

export default function Login() {
    
    const [isLoading, setLoading] = useState(false);

    const handleClick = () => setLoading(true);

    const [formValues, setFormValues] = useState({
        user: '',
        password: '',
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormValues({ ...formValues, [name]: value });
    };   
    
    const handleSubmit = (event) => {
        event.preventDefault();
        
        //Values to get
        //formValues.user
        //formValues.pasword

        

        console.log(formValues.user + " " + formValues.password);
    };
    
    return (
      <div class="loginPage">
        
        <Card style={{ width: '35rem', background: 'linear-gradient(135deg, #fde4e6, #e4d5fb, #9fbbff)',  }} className="mx-auto my-6 w-3/4 shadow" >
            <Card.Body>
            <img
              src={require("../images/macrosoftLogo.png")}
              className="mx-auto w-1/2 h-1/2 "
            />

            <Form className="p-1  w-1/2 mx-auto mt-10" onSubmit={handleSubmit}>
            <Form.Group className="mb-3" controlId="formEmail">
                <Form.Control 
                type="username"
                name="user"
                placeholder="Username or email"
                disabled={isLoading}
                onChange={handleChange}
                />
                
            </Form.Group>

            <Form.Group className="mb-3 text-right" controlId="formPassword">
                <Form.Control 
                type="password"
                name="password"
                placeholder="Password"
                disabled={isLoading}
                onChange={handleChange}
                />
                <Form.Text className="text-muted">
                <a href="">Forgot Password?</a>
                </Form.Text>
            </Form.Group>
            <Button variant="primary" type="submit" 
                className="w-full mb-3 px-auto"
                disabled={isLoading}
                onClick={!isLoading ? handleClick : null}
                >
                {isLoading ? "Logging in..." : "Log in"}
            </Button>
            <p>Don't have an account?</p>
            <NavLink className="no-underline text-inherit" to="/signup">
                <Button variant="secondary" className="w-full px-auto mb-3">
                
                    Sign Up
                
                </Button>
            </NavLink>
            </Form>


            



            </Card.Body>
        


        </Card>
        
        
        
      </div>
    );

  }