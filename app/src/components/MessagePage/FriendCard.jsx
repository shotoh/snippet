import React from "react";

export default function FriendCard({ friend, onClick }) {
  return (
    <li
      className="d-flex align-items-center mb-4"
      onClick={() => onClick(friend)}
      style={{ cursor: "pointer" }}
    >
      <img
        src={friend.profilePicture}
        alt={`${friend.displayName}'s profile`}
        className="rounded-circle me-3"
        width="50"
        height="50"
      />
      <div>
        <strong>{friend.displayName}</strong>
      </div>
    </li>
  );
}