import React from "react";
import FriendCard from "./FriendCard";
import {Image} from "react-bootstrap";

export default function FriendsBar({ friends, error }) {
  return (
    <div>
      <h1>Friends</h1>
      
      
      <FriendCard circleImage={"https://upload.wikimedia.org/wikipedia/en/a/a6/Pok%C3%A9mon_Pikachu_art.png"} 
      userDisplayName={"Jablinski Games"}/>
      
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {friends.length === 0 ? (
        <p>No Friends</p>
      ) : (
        friends.map((friend) => (
          <FriendCard
            userImage={friend.userImage}
            friendID
            />
        ))
      )}


    </div>
  );
}
