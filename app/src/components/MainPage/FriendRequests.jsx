import React, { useState } from 'react';
import { Modal, Button, Form, Spinner } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import FriendCard from "./FriendCard";

function FriendRequests({ show, handleClose, friends, onAccept, onReject, sendFriendRequest}) {
    const [createNew, setCreateNew] = useState(false); // Track if creating a new friend request
    const [username, setUsername] = useState(''); // Track entered username
    const [loading, setLoading] = useState(false); // Track loading state
    const [resultMessage, setResultMessage] = useState(''); // Track request result
    
    const handleRequest = async () => {
        setLoading(true);
        setResultMessage(''); // Clear any previous result messages

        try {
            const result = await sendFriendRequest(username); // Assuming sendFriendRequest is passed as a prop
            setResultMessage(`Request sent: ${result}`);
        } catch (error) {
            setResultMessage('Error sending friend request.');
        } finally {
            setLoading(false);
        }
    };


    return (
        <Modal show={show} onHide={handleClose} backdrop="true" centered>
        <Modal.Header closeButton>
        <Button 
                variant="primary" 
                className="ml-auto" 
                onClick={() => setCreateNew(!createNew)} // Toggle between friend list and form
            >
                <h3>+</h3>
            </Button>
            <Modal.Title className="text-center w-full">
                Friend Requests
            </Modal.Title>
            
        </Modal.Header>
        <Modal.Body style={{ height: '50vh', overflowY: 'auto' }}>
            {createNew ? (
                <Form>
                    <Form.Group controlId="username">
                        <Form.Label>Enter Username</Form.Label>
                        <Form.Control
                            type="text"
                            placeholder="Username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                        />
                    </Form.Group>
                    <Button variant="primary" className="mt-3" onClick={handleRequest} disabled={loading}>
                        {loading ? <Spinner animation="border" size="sm" /> : 'Request'}
                    </Button>
                    {resultMessage && <p>{resultMessage}</p>}
                </Form>
            ) : (
                friends.length > 0 ? (
                    friends.map((friend, index) => (
                        <FriendCard
                            key={index}
                            userImage={friend.userImage}
                            userDisplayName={friend.userDisplayName}
                            userURL={friend.userURL}
                            onAccept={() => onAccept(friend)}
                            onReject={() => onReject(friend)}
                        />
                    ))
                ) : (
                    <p>No friend requests at the moment</p>
                )
            )}
        </Modal.Body>
    </Modal>
    );
}

export default FriendRequests;