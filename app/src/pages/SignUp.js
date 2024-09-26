import React, { useState } from 'react';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import FloatingLabel from 'react-bootstrap/FloatingLabel';
import { NavLink} from "react-router-dom";
import { useNavigate } from 'react-router-dom';
import InputGroup from 'react-bootstrap/InputGroup';

export default function SignUp() {
    const [isLoading, setLoading] = useState(false);
    const [formValues, setFormValues] = useState({
        username: '',
        email: '',
        password: '',
    });
    const [validated, setValidated] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();
    
        // Simple validation check (expand this as needed)
        const isFormValid = formValues.username && formValues.email.includes('@') && formValues.password.length >= 8;

        if (isFormValid) {
            setLoading(true);
            setValidated(true);
            setErrorMessage('');

            try {
                const response = await fetch('/api/auth/register', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(formValues),
                });

                //Handles a successful registration
                const result = await response.json();
                if (response.ok && result.status === 'success') {
                    setLoading(false);
                    navigate('/login');
                } else {
                    setLoading(false);
                    setErrorMessage('Unable to register');
                }
            } catch (error) {
                setLoading(false);
                setErrorMessage('Error occured');
            }
        } else {
            setValidated(true);
        }
    };
    
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormValues({ ...formValues, [name]: value });
    };   
    
    return (
      <div className="signupPage">
        <Card style={{ width: '35rem', background: 'linear-gradient(135deg, #fde4e6, #e4d5fb, #9fbbff)'  }} className="mx-auto my-6 w-3/4 shadow" >
            <Card.Body>
            <Card.Title className="text-center mt-1"><b><h2>Create new account</h2></b></Card.Title>

            <Form noValidate validated={validated} onSubmit={handleSubmit} className="p-1  w-1/2 mx-auto mt-5">
            {/* Username Field */}
            <FloatingLabel controlId="floatingUsername" label="Username" className="mb-3">
                <Form.Control
                type="text"
                placeholder="Username"
                name="username"
                disabled={isLoading}
                value={formValues.username}
                onChange={handleChange}
                required
                isInvalid={validated && !formValues.username}
                />
                <Form.Control.Feedback type="invalid">
                Please enter your username.
                </Form.Control.Feedback>
            </FloatingLabel>

            {/* Email Field */}
            <FloatingLabel controlId="floatingEmail" label="Email address" className="mb-3">
                <Form.Control
                type="email"
                placeholder="name@example.com"
                name="email"
                disabled={isLoading}
                value={formValues.email}
                onChange={handleChange}
                required
                isInvalid={validated && !formValues.email.includes('@')}
                />
                <Form.Control.Feedback type="invalid">
                Please enter a valid email address.
                </Form.Control.Feedback>
            </FloatingLabel>

            {/* Password Field */}
            <FloatingLabel controlId="floatingPassword" label="Password" className="mb-3">
                <Form.Control
                type="password"
                placeholder="Password"
                name="password"
                disabled={isLoading}
                value={formValues.password}
                onChange={handleChange}
                required
                isInvalid={validated && (formValues.password.length < 8 || !/\d/.test(formValues.password))}
                />
                <Form.Control.Feedback type="invalid">
                Password must be at least 8 characters long and contain a number.
                </Form.Control.Feedback>
            </FloatingLabel>
            
            <Button variant="primary" type="submit" className="w-full px-auto mb-3"
                
                disabled={isLoading}
                >
                    {isLoading ? "Loading..." : "Sign up"}
                </Button>

                {errorMessage && <p className="text-danger">{errorMessage}</p>}
            
            <p className="mt-5">Already have an account?</p>
            <NavLink className="no-underline text-inherit" to="/login">
                <Button variant="secondary" className="w-full px-auto mb-3">
                    Log in
                </Button>
            </NavLink>
            </Form>
            </Card.Body>
        </Card>
      </div>
    );
}