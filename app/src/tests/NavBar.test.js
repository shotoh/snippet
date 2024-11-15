import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import NavBar from '../components/MainPage/NavBar';
import userEvent from '@testing-library/user-event';

describe('NavBar Component', () => {
  beforeEach(() => {
    localStorage.clear();
  });

  test('renders welcome message when username is available', async () => {
    const mockJwtToken = `header.${btoa(JSON.stringify({ sub: "1" }))}.signature`;
    localStorage.setItem("authToken", mockJwtToken);
    const mockUserData = { data: [{ id: 1, username: 'testUser' }] };
    
    global.fetch = jest.fn(() =>
      Promise.resolve({
        ok: true,
        json: () => Promise.resolve(mockUserData),
      })
    );

    render(
      <BrowserRouter>
        <NavBar onPostCreated={() => {}} />
      </BrowserRouter>
    );

    await waitFor(() => expect(screen.getByText('Welcome back,')).toBeInTheDocument());
    expect(screen.getByText('testUser!')).toBeInTheDocument();
  });

  test('shows "Hello Guest!" if no username found', async () => {
    const mockJwtToken = `header.${btoa(JSON.stringify({ sub: "1" }))}.signature`;
    localStorage.setItem("authToken", mockJwtToken);
    const mockUserData = { data: [] }; // No matching user data

    global.fetch = jest.fn(() =>
      Promise.resolve({
        ok: true,
        json: () => Promise.resolve(mockUserData),
      })
    );

    render(
      <BrowserRouter>
        <NavBar onPostCreated={() => {}} />
      </BrowserRouter>
    );

    await waitFor(() => expect(screen.getByText('Hello Guest!')).toBeInTheDocument());
  });

  test('displays settings modal when "Settings" is clicked', async () => {
    render(
      <BrowserRouter>
        <NavBar onPostCreated={() => {}} />
      </BrowserRouter>
    );

    const dropdown = screen.getByTestId('nav-dropdown'); 
    
    await waitFor(() => userEvent.click(dropdown));

    const settingsButton = screen.getByText('Settings');
    fireEvent.click(settingsButton);

    await waitFor(() => expect(screen.getByText('Settings')).toBeInTheDocument());
  });

  test('opens post creator modal when "Create Post" is clicked', async () => {
    render(
      <BrowserRouter>
        <NavBar onPostCreated={() => {}} />
      </BrowserRouter>
    );

    const dropdown = screen.getByTestId('nav-dropdown'); 
    
    await waitFor(() => userEvent.click(dropdown));

    const createPostButton = screen.getByText('Create Post');
    fireEvent.click(createPostButton);

    await waitFor(() => expect(screen.getByText('Select Images/Videos')).toBeInTheDocument());
  });

  test('logs out and redirects to login when "Logout" is clicked', async () => {
    localStorage.setItem('authToken', 'dummyToken');

    render(
      <BrowserRouter>
        <NavBar onPostCreated={() => {}} />
      </BrowserRouter>
    );
    
    const dropdown = screen.getByTestId('nav-dropdown'); 
    
    await waitFor(() => userEvent.click(dropdown));

    const logoutButton = screen.getByText('Logout');
    await waitFor(() => fireEvent.click(logoutButton));

    expect(localStorage.getItem('authToken')).toBe(null);
    expect(window.location.href).toContain('/login');
  });

  test('renders the "Sign In" button if the user is not logged in', async () => {
    render(
      <BrowserRouter>
        <NavBar onPostCreated={() => {}} />
        
      </BrowserRouter>
    );
    
    const dropdown = screen.getByTestId('nav-dropdown'); 
    
    // Open the dropdown
    await waitFor(() => userEvent.click(dropdown));

    expect(screen.getByText('Sign In')).toBeInTheDocument();
  });
});
