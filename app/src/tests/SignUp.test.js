import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import SignUp from './../pages/SignUp';
import { BrowserRouter } from 'react-router-dom';

const renderComponent = () =>
    render(
        <BrowserRouter>
            <SignUp />
        </BrowserRouter>
    );

test('renders Sign Up form fields and buttons', () => {
    renderComponent();

    // Check if form elements are present
    expect(screen.getByLabelText(/Username/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Email address/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Password/i)).toBeInTheDocument();
    expect(screen.getByRole('button', { name: /Sign up/i })).toBeInTheDocument();
    expect(screen.getByRole('button', { name: /Log in/i })).toBeInTheDocument();
});

test('displays validation errors for invalid inputs', async () => {
    renderComponent();

    // Try submitting with empty fields
    fireEvent.click(screen.getByRole('button', { name: /Sign up/i }));
    
    // Check for validation errors
    await waitFor(() => {
        expect(screen.getByText(/Please enter your username/i)).toBeInTheDocument();
        expect(screen.getByText(/Please enter a valid email address/i)).toBeInTheDocument();
        expect(screen.getByText(/Password must be at least 8 characters long and contain a number/i)).toBeInTheDocument();
    });
});

test('validates password and email fields', async () => {
    renderComponent();

    // Input invalid email
    fireEvent.change(screen.getByLabelText(/Email address/i), { target: { value: 'invalid-email' } });
    fireEvent.blur(screen.getByLabelText(/Email address/i));
    
    // Input invalid password
    fireEvent.change(screen.getByLabelText(/Password/i), { target: { value: 'short' } });
    fireEvent.blur(screen.getByLabelText(/Password/i));

    // Submit form
    fireEvent.click(screen.getByRole('button', { name: /Sign up/i }));

    await waitFor(() => {
        expect(screen.getByText(/Please enter a valid email address/i)).toBeInTheDocument();
        expect(screen.getByText(/Password must be at least 8 characters long and contain a number/i)).toBeInTheDocument();
    });
});

test('submits form with valid inputs', async () => {
    renderComponent();

    // Mock a successful form submission
    global.fetch = jest.fn(() =>
        Promise.resolve({
            ok: true,
            json: () => Promise.resolve({ status: 'success' }),
        })
    );

    // Fill in form fields
    fireEvent.change(screen.getByLabelText(/Username/i), { target: { value: 'validUser' } });
    fireEvent.change(screen.getByLabelText(/Email address/i), { target: { value: 'valid@example.com' } });
    fireEvent.change(screen.getByLabelText(/Password/i), { target: { value: 'validPass1' } });

    // Submit the form
    fireEvent.click(screen.getByRole('button', { name: /Sign up/i }));

    // Expect loading state
    expect(screen.getByText(/Loading.../i)).toBeInTheDocument();

    await waitFor(() => {
        // After successful submission, check for redirection or success message
        expect(global.fetch).toHaveBeenCalledWith('/api/auth/register', expect.anything());
    });
    
    global.fetch.mockRestore();
});