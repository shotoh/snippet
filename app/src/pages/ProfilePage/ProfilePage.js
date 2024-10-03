import React from "react";

import NavBar from "../../components/MainPage/NavBar";
import ProfileBanner from "../../components/ProfilePage/ProfileBanner";
import ProfileInfo from "../../components/ProfilePage/ProfileInfo";

const ProfilePage = () => {
  return (
    <div className="min-h-screen bg-slate-200 flex flex-col">
      <NavBar />
      <ProfileBanner />
      <ProfileInfo />
    </div>
  );
};

export default ProfilePage;
