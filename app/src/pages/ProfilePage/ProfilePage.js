import React, {useState} from "react";
import NavBar from "../../components/MainPage/NavBar";
import ProfileBanner from "../../components/ProfilePage/ProfileBanner";
import ProfileInfo from "../../components/ProfilePage/ProfileInfo";
import ProfileFeed from "../../components/ProfilePage/ProfileFeed";
import useProfileData from "../../hooks/useProfileData";
import EditableProfileModal from "../../components/ProfilePage/EditableProfileModal";

const ProfilePage = () => {
  const { userData, posts, error, fetchData, userIdToDisplay, submitNewUserData, showModal, closeModal, buttonType, openModal, addFriend, removeFriend } =
    useProfileData();



  return (
    <div className="min-h-screen bg-slate-200 flex flex-col">
      <EditableProfileModal onSubmit={submitNewUserData}  onClose={closeModal} displayName={userData.username} biography={userData.biography} show={showModal} image={userData.profilePicture}/>
      <NavBar username={userData.username} onPostCreated={fetchData} />
      <div className="flex-grow">
        <div className="max-w-screen-xl mx-auto px-4">
          <div className="grid grid-cols-12 gap-6 mt-4">
            {/* Left Column: Profile Banner and Info */}
            <div className="col-span-12 md:col-span-4">
              <div className="bg-white rounded-lg shadow-md overflow-hidden">
                <ProfileBanner
                  banner={userData.profileBanner}
                  profilePicture={userData.profilePicture}
                />
                <ProfileInfo
                  username={userData.username}
                  handle={userData.handle}
                  biography={userData.biography}
                  id={userIdToDisplay}
                  friendCount={userData.friendCount}
                  posts={posts}
                  buttonShown={buttonType}
                  openModal={openModal}
                  addFriend={addFriend}
                  removeFriend={removeFriend}
                />
              </div>
            </div>
            {/* Right Column: User's Posts */}
            <div className="col-span-12 md:col-span-8">
              <div className="bg-white rounded-lg p-6 shadow-md">
                <ProfileFeed posts={posts} error={error} />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;
