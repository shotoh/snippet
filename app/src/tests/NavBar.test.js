import {React, act} from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import NavBar from '../components/MainPage/NavBar';
import userEvent from '@testing-library/user-event';
import { parseJwt } from '../api/MessageAPI';

describe('NavBar Component', () => {
  beforeEach(() => {
    localStorage.clear();
    global.fetch = jest.fn(() =>
      Promise.resolve({
        ok: true, // Include the ok property
        json: () => Promise.resolve({
          data: [
            { id: 1, username: "testUser", profilePicture: "" },
          ],
        }),
      })
    );
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  test('renders welcome message when username is available', async () => {
    const mockJwtToken = `header.${btoa(JSON.stringify({ sub: "1" }))}.signature`;
    localStorage.setItem("authToken", mockJwtToken);
    const mockUserData = { data: [{ id: 1, username: 'testUser' }] };
    

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
    const mockUserData = {data: [{ id: 1, username: 'testUser'}] };
    const mockJwtToken = `header.${btoa(JSON.stringify({ sub: "1" }))}.signature`;
    
    
    
    render(
      <BrowserRouter>
        <NavBar onPostCreated={() => {}} />
      </BrowserRouter>
    );

    // Wait for the dropdown to appear with the mock data
    
    const dropdown = await waitFor(() => screen.getByTestId('nav-dropdown'));

    await act(() => {
      userEvent.click(dropdown);
    });
    

    // Wait for the "Settings" button to be rendered
    const settingsButton = await waitFor(() => screen.getByText('Settings'));

    await act(() => {
      userEvent.click(settingsButton);

    });

    // Ensure the settings modal is displayed
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
