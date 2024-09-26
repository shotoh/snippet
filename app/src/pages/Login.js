import React, { useState } from 'react';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import { NavLink, useNavigate} from "react-router-dom";

export default function Login() {
    
    const [isLoading, setLoading] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const navigate = useNavigate();

    const [formValues, setFormValues] = useState({
        user: '',
        password: '',
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormValues({ ...formValues, [name]: value });
    };   
    
    const handleSubmit = async (event) => {
        event.preventDefault();
        setLoading(true);
        setErrorMessage('');

        try {
            const response = await fetch('/api/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    username: formValues.user,
                    password: formValues.password,
                }),
            });

            const result = await response.json();

            if (response.ok && result.status === 'success') {
                //Save token to localStorage
                localStorage.setItem('authToken', result.data.token);

                //Home is a placeholder for main page since we dont have it yet
                navigate('/home');
            } else {
                setErrorMessage('Invalid username or password');
                setLoading(false);
            }
        } catch (error) {
            setErrorMessage('Error occured while logging in');
            setLoading(false);
        }
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
                type="text"
                name="user"
                placeholder="Username or email"
                disabled={isLoading}
                onChange={handleChange}
                required
                />
            </Form.Group>

            <Form.Group className="mb-3 text-right" controlId="formPassword">
                <Form.Control 
                type="password"
                name="password"
                placeholder="Password"
                disabled={isLoading}
                onChange={handleChange}
                required
                />
                <Form.Text className="text-muted">
                <a href="">Forgot Password?</a>
                </Form.Text>
            </Form.Group>

            <Button variant="primary" type="submit" 
                className="w-full mb-3 px-auto"
                disabled={isLoading}
                >
                {isLoading ? "Logging in..." : "Log in"}
            </Button>

            {errorMessage && <p className="text-danger">{errorMessage}</p>}

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