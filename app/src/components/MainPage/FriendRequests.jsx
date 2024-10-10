import React from 'react';
import { Modal, Button, Image, Card } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import FriendCard from "./FriendCard";

function FriendRequests({ show, handleClose, friends, onAccept, onReject }) {
    return (
        <Modal show={show} onHide={handleClose} backdrop="true" centered>
            <Modal.Header closeButton>
                <Modal.Title className="text-center w-full">Friend Requests</Modal.Title>
            </Modal.Header>
            <Modal.Body style={{ height: '50vh', overflowY: 'auto' }}>
                
                {friends.length > 0 ? (
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
                )}
            </Modal.Body>
        </Modal>
    );
}

export default FriendRequests;