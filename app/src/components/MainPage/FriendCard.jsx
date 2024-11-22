import React, { useEffect } from "react";
import { Card, Image, Button } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import defaultProfile from "../../images/defaultprofile.png";
function FriendCard({
  userImage,
  username,
  userDisplayName,
  userURL,
  onAccept,
  onReject,
}) {
  const navigate = useNavigate();

  const onCircleClick = () => {
    navigate(userURL);
  };

  useEffect(() => {
    console.log("Friend Cards: " + username + " " + userDisplayName);
  }, []);

  return (
    <div className="flex flex-row w-full">
      <Link to={userURL} className="no-underline flex-grow">
        <Button variant="light" className="w-full">
          <div className="flex justify-around">
            <Image
              src={userImage || defaultProfile}
              alt="User image"
              roundedCircle
              className="my-auto"
              style={{
                width: "50px",
                height: "50px",
                cursor: "pointer",
                border: "0px solid white",
              }}
            />
            <Card.Body className="my-auto ml-2 text-left">
              {userDisplayName ? userDisplayName : username}
            </Card.Body>
          </div>
        </Button>
      </Link>

      {onAccept !== undefined && onReject !== undefined && (
        <div className="flex items-center">
          <Button variant="success" className="mr-2" onClick={onAccept}>
            ✓
          </Button>
          <Button variant="danger" onClick={onReject}>
            ✗
          </Button>
        </div>
      )}
    </div>
  );
}

export default FriendCard;
