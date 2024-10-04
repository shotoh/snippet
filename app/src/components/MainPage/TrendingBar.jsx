import React from "react";
import TrendingCard from "./TrendingCard.jsx";

export default function TrendingBar() {
  
  const fetchPosts = async (event) => {
      
  }


  const createTrendingCard = () => {
      
  }
  
  
  return (
    <div className="w-full h-full">
      <div className="mx-auto pt-2">
        <h3 className="text-center">See What's</h3>
        <h1 className="text-center "><b>Trending</b></h1>
      </div>
      <div id="TrendingContentCreator" className="px-2">
        <TrendingCard 
          backgroundImage="https://variety.com/wp-content/uploads/2018/12/Jack-Black.jpg"
          circleImage="https://yt3.googleusercontent.com/ytc/AIdro_mv2AHfwkrm0yOrdapvdVzoW0x3t-pBnADG-VXB3uqqeHo=s160-c-k-c0x00ffffff-no-rj"

        />
      </div>
    </div>
  );
}
