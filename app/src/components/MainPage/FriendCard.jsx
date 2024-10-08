import React from "react";
import { Card, Image } from 'react-bootstrap';
import {Link } from "react-router-dom";

function FriendCard({userImage, userDisplayName}) {
    
    
    return (
        <Link to="/">
        <Card>
            <div className="flex justify-around no-underline">
                <Image 
                src={userImage}
                roundedCircle
                
                style={{
                    width: '50px',
                    height: '50px',
                    cursor: 'pointer',
                    border: '0px solid white',
                }}
                />
                <Card.Body className="no-underline">{userDisplayName}</Card.Body>
                
            </div>
            
        </Card>
        </Link>
        
        
      );  
} 


export default FriendCard;