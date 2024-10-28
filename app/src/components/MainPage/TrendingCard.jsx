import React from "react";
import { Card, Image } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

const TrendingCard = ({ backgroundImage, circleImage, postURL, userURL }) => {
  const navigate = useNavigate();

  const onCardClick = () => {
    navigate(postURL);
  };

  const onCircleClick = () => {
    navigate(userURL);
  };

  return (
    <Card
      className="custom-card mt-3"
      onClick={onCardClick}
      style={{
        width: "100%",
        paddingTop: "56.25%", // 16:9 aspect ratio
        backgroundImage: `url(${backgroundImage})`,
        backgroundSize: "cover",
        backgroundPosition: "center",
        position: "relative",
        cursor: "pointer",
        boxShadow: "0 4px 8px rgba(0, 0, 0, 0.2)",
      }}
    >
      <div
        style={{
          position: "absolute",
          bottom: "10px",
          right: "10px",
        }}
      >
        <Image
          src={circleImage}
          roundedCircle
          onClick={(e) => {
            e.stopPropagation(); // Prevents the card's onClick event
            onCircleClick();
          }}
          style={{
            width: "50px",
            height: "50px",
            cursor: "pointer",
            border: "0px solid white",
          }}
        />
      </div>
    </Card>
  );
};

export default TrendingCard;
