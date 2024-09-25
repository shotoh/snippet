import React, { useState } from 'react';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import FloatingLabel from 'react-bootstrap/FloatingLabel';
import { NavLink} from "react-router-dom";
import InputGroup from 'react-bootstrap/InputGroup';

export default function SignUp() {
    
    const [isLoading, setLoading] = useState(false);

    const handleClick = () => setLoading(true);
    
    const [formValues, setFormValues] = useState({
        username: '',
        email: '',
        password: '',
    });

    const [validated, setValidated] = useState(true);
    
    const handleSubmit = (event) => {
        event.preventDefault();
        
    
        // Simple validation check (expand this as needed)
        const isFormValid = formValues.username && formValues.email.includes('@') && formValues.password.length >= 8;

        if (isFormValid) {
            // Proceed with form submission
            //Values to get
            //formValues.username
            //formValues.email
            //formValues.pasword
            setValidated(true);
            validated = true;
        } else {
            validated = false;
        }
    };
    
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormValues({ ...formValues, [name]: value });
        setValidated(false);
    };   
    
    return (
      <div class="signupPage">
        <Card style={{ width: '35rem', background: 'linear-gradient(135deg, #fde4e6, #e4d5fb, #9fbbff)',  }} className="mx-auto my-6 w-3/4 shadow" >
            <Card.Body>
            <Card.Title className="text-center mt-1"><b><h2>Create new account</h2></b></Card.Title>

            <Form validated={validated} onSubmit={handleSubmit} className="p-1  w-1/2 mx-auto mt-5">
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
                isInvalid={!validated && !formValues.username}
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
                isInvalid={!validated && !formValues.email.includes('@')}
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
                isInvalid={formValues.password.length < 8 || !/\d/.test(formValues.password)}
                />
                <Form.Control.Feedback type="invalid">
                Password must be at least 8 characters long and contain a number.
                </Form.Control.Feedback>
            </FloatingLabel>
            
            <Button variant="primary" type="submit" className="w-full px-auto mb-3"
                
                disabled={isLoading}
                onClick={!isLoading ? handleClick : null}
                >
                    {isLoading ? "Loading..." : "Sign up"}
                </Button>
            
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


/*
<InputGroup hasValidation>
                <FloatingLabel className="mb-3" controlId="formNewUsername" label="Username">
                    
                    <Form.Control type="username" placeholder="Username" 
                    onChange={(e) => setInputValue(e.target.value)}
                    required
                    isInvalid={validated && !inputValue.includes('@')}/>
                    <Form.Control.Feedback type="invalid">
                        Please choose a username.
                    </Form.Control.Feedback>
                </FloatingLabel>
            </InputGroup>
            
            <InputGroup hasValidation>
                <FloatingLabel className="mb-3" controlId="formNewEmail" label="Email">
                    <Form.Control type="email" placeholder="Email" />
                    <Form.Control.Feedback type="invalid">
                        Please enter a valid email address.
                    </Form.Control.Feedback>
                </FloatingLabel>
            </InputGroup>

            <InputGroup hasValidation>
                <FloatingLabel className="mb-3" controlId="formPassword" label="Password">
                    <Form.Control type="password" placeholder="Password" 
                    onChange={(e) => setInputValue(e.target.value)}
                    required
                    isInvalid={validated && !inputValue.includes('@')}/>
                    <Form.Control.Feedback type="invalid">
                            Please enter a valid password.
                        </Form.Control.Feedback>
                </FloatingLabel>
            </InputGroup>

*/

  }