import React from "react";

import NavBar from "../../components/MainPage/NavBar";
import TrendingBar from "../../components/MainPage/TrendingBar";
import Feed from "../../components/MainPage/Feed";
import FriendsBar from "../../components/MainPage/FriendsBar";

const MainPage = () => {
  return (
    <div className="min-h-screen bg-slate-200 flex flex-col">
      <NavBar />
      <div className="flex-grow grid grid-cols-12 gap-4 mt-4 pr-4">
        {/* Trending Bar */}
        <div className="col-span-3 bg-orange-400">
          <TrendingBar />
        </div>

        {/* Feed */}
        <div className="col-span-6 bg-sky-500">
          <Feed />
        </div>

        {/* Friends Bar */}
        <div className="col-span-3 bg-purple-400">
          <FriendsBar />
        </div>
      </div>
    </div>
  );
};

export default MainPage;
