import React from "react";

import NavBar from "../../components/MainPage/NavBar";
import FriendsBar from "../../components/MainPage/FriendsBar";
import Feed from "../../components/MainPage/Feed";
import TrendingBar from "../../components/MainPage/TrendingBar";

const MainPage = () => {
  return (
    <div className="min-h-screen bg-slate-200">
      <NavBar />
    </div>
  );
};

export default MainPage;
