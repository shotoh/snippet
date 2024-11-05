import React from "react";
import { InputGroup, Form } from "react-bootstrap";
import FriendCard from "./FriendCard";

export default function FriendsList({ friends, onSelectFriend }) {
  return (
    <div>
      <h2 className="font-montserrat font-bold">Messages</h2>
      <InputGroup className="mb-3">
        <Form.Control placeholder="Search Messages" aria-label="Search Messages" />
        <InputGroup.Text>
          <i className="bi bi-search"></i>
        </InputGroup.Text>
      </InputGroup>
      <ul className="list-unstyled">
        {friends.map((friend) => (
          <FriendCard
            key={friend.id}
            friend={friend}
            onClick={() => onSelectFriend(friend)}
          />
        ))}
      </ul>
    </div>
  );
}