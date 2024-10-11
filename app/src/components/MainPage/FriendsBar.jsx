import React, { useLayoutEffect } from "react";
import FriendCard from "./FriendCard";
import {Image} from "react-bootstrap";
import { useState } from "react";
import FriendRequests from "./FriendRequests";

export default function FriendsBar({ friends, friendRequests, error, sendFriendRequest }) {
  
  const [notifications, setNotifications] = useState(2);
  const [showModal, setShowModal] = useState(false);
  
  const [dummyFriendRequests, setDummyFriendRequests] = useState([
    { userImage: 'https://www.models-resource.com/resources/big_icons/15/14997.png?updated=1460924471', userDisplayName: 'Kowalski', userURL: '/profile/kowalksi', },
    { userImage: 'https://images.genius.com/6ee6e843687e01ce4385cc481cd7bc17.750x750x1.jpg', userDisplayName: 'Phil Swift', userURL: '/profile/philswift' },
    { userImage: 'https://images.genius.com/6ee6e843687e01ce4385cc481cd7bc17.750x750x1.jpg', userDisplayName: 'Phil Swift', userURL: '/profile/philswift' },

  ]);


  function onAccept(friend) {
      console.log(friend);
  }
  
  function onReject(friend) {
    console.log(friend);
  }

  function openModal() {
    setShowModal(true);
  }

  return (
    <div className="px-2 h-full flex flex-col justify-between h-full">

      <FriendRequests friends={dummyFriendRequests} onAccept={onAccept} onReject={onReject} show={showModal} handleClose={() => setShowModal(false)} sendFriendRequest={sendFriendRequest}/>

      <div id="FriendsTop" className="flex ml-5 my-2 w-full">
        <h1 className="tracking-wide"><b>Friends</b></h1>
        <div className="my-auto" style={{ position: 'relative', display: 'inline-block' }}>
          <Image
            src="https://cdn.iconscout.com/icon/free/png-512/free-bell-icon-download-in-svg-png-gif-file-formats--notification-alarm-ring-clock-user-interface-pack-icons-1502555.png?f=webp&w=256"
            roundedCircle
            className="my-auto ml-8 float-left"
            onClick={(e) => {
              e.stopPropagation(); // Prevents the card's onClick event
              openModal();
            }}
            style={{
              width: '20px',
              height: '20px',
              cursor: 'pointer',
              border: '0px solid white',
            }}
          />
          {notifications > 0 &&
            <span 
            style={{
              position: 'absolute',
              top: '-5px', // Adjust to move it higher or lower
              right: '-5px', // Adjust to move it left or right
              backgroundColor: 'red',
              color: 'white',
              borderRadius: '50%',
              width: '15px',
              height: '15px',
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
              fontSize: '10px'
            }}
            onClick={(e) => {
              e.stopPropagation(); // Prevents the card's onClick event
              openModal();
          }}
          >
            {friendRequests !== undefined ? friendRequests.length : 0} {/* Number inside the circle */}
          </span>
          }
          
        </div>

      </div>
      
      <div id="FriendList" className="overflow-y-auto overscroll-y-contain"
        style={{"height": "100%"}}
      
      >
        <FriendCard 
        userImage="https://preview.redd.it/which-surprised-pikachu-is-best-v0-9ljw32a35w4b1.png?width=778&format=png&auto=webp&s=dbed072b5593e3851315f7e6d5bda406ade8ad90"
        userDisplayName="Perpetually Surprised Pikachu"
        userURL="/profile/psp"
        />
        <FriendCard 
        userImage="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSnOCVtC_rE-XcVK0qi-f_F_zJh3-pAT3EQZQ&s"
        userDisplayName="Reginald"
        userURL="/profile/Reginald"
        />


        {error && <p style={{ color: 'red' }}>{error}</p>}
        {friends.length === 0 ? (
          <p>Error: Fetched 0 Friends</p>
        ) : (
          friends.map((friend) => (
            <FriendCard
              userImage={friend.userImage}
              friendID
              />
          ))
        )}

      </div>
      
      <div id="FriendExtra" className="h-auto">
        <a href="/home/contact" className="text-gray-500 leading-none"><p>Contact Us</p></a>
        <a href="" className="text-gray-500 leading-none"><p>Report an Issue</p></a>
        <p className="text-gray-300 leading-none">Made possible by Macrosoft LLC</p>
      </div>

    </div>
  );
}
