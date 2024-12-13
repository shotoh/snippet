import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import PostCreator from '../components/MainPage/PostCreator';
import userEvent from '@testing-library/user-event';

describe('Post Creator Component', () => {
    let mockHandleClose = jest.fn();
    let mockOnPostCreate = jest.fn();
    
    beforeEach(() => {
        localStorage.clear();
        render(
            <PostCreator show={true} handleClose={mockHandleClose} onPostCreate={mockOnPostCreate}/>
        )
    });
    

    test("renders the PostCreator modal with initial elements", () => {
    
    expect(screen.getByPlaceholderText("Write post content here")).toBeInTheDocument();
    expect(screen.getByText("Select Images/Videos")).toBeInTheDocument();
    });



    test('displays error when trying to create post without content', async () => {
    fireEvent.click(screen.getByText("Create"));
    await waitFor(() => {
    expect(screen.getByText("Text content is required")).toBeInTheDocument();
    });


    });


    test("displays error when user is unauthenticated", async () => {
    
    fireEvent.change(screen.getByPlaceholderText("Write post content here"), { target: { value: "Test post content" } });
    fireEvent.click(screen.getByText("Create"));

    await waitFor(() => {
        expect(screen.getByText("User is not authenticated")).toBeInTheDocument();
    });
    });

    

});