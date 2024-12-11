import React from "react";
import DefaultProfile from "../../images/defaultprofile.png";

export default function FriendCard({ friend, onClick }) {
  return (
    <li
      className="d-flex align-items-center transition ease-in-out hover:bg-slate-100"
      onClick={() => onClick(friend)}
      style={{ cursor: "pointer" }}
    >
      <img
        src={friend.profilePicture ? `/public/${friend.profilePicture}` : DefaultProfile}
        alt={`${friend.displayName}'s profile`}
        className="rounded-circle me-3 my-2"
        width="50"
        height="50"
      />
      <div>
        <strong>{friend.displayName}</strong>
      </div>
    </li>
  );
}
