import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import FriendRequests from "../components/MainPage/FriendRequests";
import { BrowserRouter } from 'react-router-dom';
import FriendCard from "../components/MainPage/FriendCard";


describe("FriendRequests Component", () => {
  const mockHandleClose = jest.fn();
  const mockSendFriendRequest = jest.fn();
  const mockOnAccept = jest.fn();
  const mockOnReject = jest.fn();

  afterEach(() => {
    jest.clearAllMocks();
  });

  test("renders the modal when 'show' prop is true", () => {
    render(
      <FriendRequests
        show={true}
        handleClose={mockHandleClose}
        friends={[]}
        onAccept={mockOnAccept}
        onReject={mockOnReject}
        sendFriendRequest={mockSendFriendRequest}
        createNew={false}
      />
    );

    expect(screen.getByText("Friend Requests")).toBeInTheDocument();
  });

  test("does not render the modal when 'show' prop is false", () => {
    const { queryByText } = render(
      <FriendRequests
        show={false}
        handleClose={mockHandleClose}
        friends={[]}
        onAccept={mockOnAccept}
        onReject={mockOnReject}
        sendFriendRequest={mockSendFriendRequest}
        createNew={false}
      />
    );

    expect(queryByText("Friend Requests")).not.toBeInTheDocument();
  });

  

  test("renders the friend request form when 'createNew' is true", () => {
    render(
      <FriendRequests
        show={true}
        handleClose={mockHandleClose}
        friends={[]}
        onAccept={mockOnAccept}
        onReject={mockOnReject}
        sendFriendRequest={mockSendFriendRequest}
        createNew={true}
      />
    );

    expect(screen.getByLabelText("Enter Username")).toBeInTheDocument();
    expect(screen.getByPlaceholderText("Username")).toBeInTheDocument();
  });

  test("handles sending a friend request", async () => {
    mockSendFriendRequest.mockResolvedValue("Success");

    render(
      <FriendRequests
        show={true}
        handleClose={mockHandleClose}
        friends={[]}
        onAccept={mockOnAccept}
        onReject={mockOnReject}
        sendFriendRequest={mockSendFriendRequest}
        createNew={true}
      />
    );

    fireEvent.change(screen.getByPlaceholderText("Username"), {
      target: { value: "testuser" },
    });
    fireEvent.click(screen.getByText("Request"));

    expect(mockSendFriendRequest).toHaveBeenCalledWith("testuser");
    await waitFor(() =>
      expect(screen.getByText("Request: Success")).toBeInTheDocument()
    );
  });

  test("handles sending a friend request failure", async () => {
    mockSendFriendRequest.mockRejectedValue(new Error("Error"));

    render(
      <FriendRequests
        show={true}
        handleClose={mockHandleClose}
        friends={[]}
        onAccept={mockOnAccept}
        onReject={mockOnReject}
        sendFriendRequest={mockSendFriendRequest}
        createNew={true}
      />
    );

    fireEvent.change(screen.getByPlaceholderText("Username"), {
      target: { value: "testuser" },
    });
    fireEvent.click(screen.getByText("Request"));

    await waitFor(() =>
      expect(screen.getByText("Error sending friend request.")).toBeInTheDocument()
    );
  });

  test("toggles between friend list and form", () => {
    const { getByText, queryByLabelText } = render(
      <FriendRequests
        show={true}
        handleClose={mockHandleClose}
        friends={[]}
        onAccept={mockOnAccept}
        onReject={mockOnReject}
        sendFriendRequest={mockSendFriendRequest}
        createNew={false}
      />
    );

    fireEvent.click(getByText("+"));
    expect(queryByLabelText("Enter Username")).toBeInTheDocument();
  });
});
