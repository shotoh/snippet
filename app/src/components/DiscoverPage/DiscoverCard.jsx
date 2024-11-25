import React, { useState, useEffect } from "react";
import DefaultProfile from "../../images/defaultprofile.png";
import { Carousel } from "react-bootstrap";

export default function DiscoverCard({ discoverPost }) {
  const {
    id,
      user: { userID, name},
      media,
  } = discoverPost;

  const profileURL = `/snippet/user/${userID}`;

  return (
    <div className="grid grid-rows-[21rem_auto] border rounded-lg overflow-hidden shadow-md font-montserrat">
      {/* Meda */}
      <div className="relative overflow-hidden bg-gray-100 h-[21rem]">
        {media.length > 1 ? (
          <Carousel 
            interval={null}
            slide={false}
            className="flex justify-center items-center w-full h-full overflow-hidden"
          >
            {media.map((file, index) => (
              <Carousel.Item key={index}>
                <img
                  className="max-h-[21rem] w-full h-full object-scale-down"
                  src={`/public/${file}`}
                  alt={`media-${index}`}
                />
              </Carousel.Item>
            ))}
          </Carousel>
        ) : (
          <div className="w-full h-full relative">
            <img
              className="absolute inset-0 w-full h-full object-scale-down"
              src={media.length === 0 ? DefaultProfile : `/public/${media[0]}`}
              alt="media"
            />
          </div>
        )}
      </div>

      {/* User info */}
      <div className="flex items-center justify-center bg-white py-4">
        <a href={profileURL} className="flex flex-col items-center no-underline text-black">
          <img
            src={discoverPost.user.profilePicture || DefaultProfile}
            alt="Profile"
            className="w-12 h-12 rounded-full object-cover"
          />
          <span className="mt-2 text-sm font-semibold"> {name} </span>
        </a>
      </div>
    </div>
  );
}