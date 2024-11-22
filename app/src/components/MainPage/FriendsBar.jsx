import React, { useState, useEffect } from "react";
import FriendCard from "./FriendCard";
import { Image, Button } from "react-bootstrap";
import FriendRequests from "./FriendRequests";
import defaultProfile from "../../images/defaultprofile.png";

export default function FriendsBar({
  friends,
  friendRequests,
  error,
  sendFriendRequest,
  denyFriendRequest,
}) {
  const [notifications, setNotifications] = useState(2);
  const [showModal, setShowModal] = useState(false);
  const [createNew, setCreateNew] = useState(false);

  

  function onAccept(friend) {
    console.log(friend);
    sendFriendRequest(friend.username);
  }

  function onReject(friend) {
    console.log(friend);
    denyFriendRequest(friend.username);
  }

  function openModal() {
    setCreateNew(false);
    setShowModal(true);
  }

  function openModalCreate() {
    setCreateNew(true);
    setShowModal(true);
  }

  useEffect(() => {
    if (friendRequests) {
      setNotifications(friendRequests.length);
    } else {
      setNotifications(0);
    }
  }, [friendRequests]);

  return (
    <div className="px-2 h-full flex flex-col justify-between h-full">
      <FriendRequests
        friends={friendRequests}
        onAccept={onAccept}
        onReject={onReject}
        show={showModal}
        handleClose={() => setShowModal(false)}
        sendFriendRequest={sendFriendRequest}
        createNew={createNew}
      />

      <div id="FriendsTop" className="flex ml-5 my-2 w-full">
        <h1 className="tracking-wide">
          <b>Friends</b>
        </h1>
        <div
          className="my-auto"
          style={{ position: "relative", display: "inline-block" }}
        >
          <Image
            src="https://cdn.iconscout.com/icon/free/png-512/free-bell-icon-download-in-svg-png-gif-file-formats--notification-alarm-ring-clock-user-interface-pack-icons-1502555.png?f=webp&w=256"
            roundedCircle
            className="my-auto ml-8 float-left"
            onClick={(e) => {
              e.stopPropagation(); // Prevents the card's onClick event
              openModal();
            }}
            style={{
              width: "20px",
              height: "20px",
              cursor: "pointer",
              border: "0px solid white",
            }}
          />
          {notifications > 0 && (
            <span
              style={{
                position: "absolute",
                top: "-5px", // Adjust to move it higher or lower
                right: "-5px", // Adjust to move it left or right
                backgroundColor: "red",
                color: "white",
                borderRadius: "50%",
                width: "15px",
                height: "15px",
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                fontSize: "10px",
              }}
              onClick={(e) => {
                e.stopPropagation(); // Prevents the card's onClick event
                openModal();
              }}
            >
              {friendRequests !== undefined ? friendRequests.length : 0}{" "}
              {/* Number inside the circle */}
            </span>
          )}
        </div>
        <Button
          variant="primary"
          className="my-1 ml-10"
          onClick={openModalCreate}
        >
          Add Friend
        </Button>
      </div>

      <div
        id="FriendList"
        className="overflow-y-auto overscroll-y-contain"
        style={{ height: "100%" }}
      >
        {error && <p style={{ color: "red" }}>{error}</p>}
        {friends && friends.length === 0 ? (
          <p>No friends</p>
        ) : (
          friends.map((friend) => (
            <FriendCard
              userImage={friend.userImage}
              username={friend.username}
              friendID
              userURL={`/snippet/user/${friend.id}`}
            />
          ))
        )}
      </div>

      <div id="FriendExtra" className="h-auto">
        <a href="/home/contact" className="text-gray-500 leading-none">
          <p>Contact Us</p>
        </a>
        <a href="" className="text-gray-500 leading-none">
          <p>Report an Issue</p>
        </a>
        <p className="text-gray-300 leading-none">
          Made possible by Macrosoft LLC
        </p>
      </div>
    </div>
  );
}
