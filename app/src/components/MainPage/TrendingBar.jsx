import React from "react";
import TrendingCard from "./TrendingCard.jsx";

export default function TrendingBar({ posts, error }) {
  const fetchPosts = async (event) => {};

  const createTrendingCard = () => {};

  return (
    <div className="w-full h-full px-3">
      <div className="text-center py-4 font-montserrat leading-tight text-black border-b border-gray-400">
        <span className="text-2xl">See what's</span> <br />
        <span className="text-5xl font-bold">Trending</span>
      </div>
      <div id="TrendingContentCreator">
        <TrendingCard
          backgroundImage="https://variety.com/wp-content/uploads/2018/12/Jack-Black.jpg"
          circleImage="https://yt3.googleusercontent.com/ytc/AIdro_mv2AHfwkrm0yOrdapvdVzoW0x3t-pBnADG-VXB3uqqeHo=s160-c-k-c0x00ffffff-no-rj"
          userURL={"/login"}
        />
        {error && <p style={{ color: "red" }}>{error}</p>}
        {posts.length === 0 ? (
          <p>No posts available</p>
        ) : (
          posts.map((post) => (
            <TrendingCard
              backgroundImage={post.backgroundImage}
              circleImage={post.circleImage}
              postURL={post.postURL}
              userURL={post.userURL}
            />
          ))
        )}
      </div>
    </div>
  );
}
