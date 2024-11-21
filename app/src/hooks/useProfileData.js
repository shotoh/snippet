import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import {
  parseJwt,
  fetchUserAndPosts,
  getFriendData,
  updateUserData,
  createFriendRequest,
} from "../api/ProfileAPI";
import DefaultProfilePicture from "../images/defaultprofile2.jpg";
import DefaultBanner from "../images/somepicture.jpg";

const useProfileData = () => {
  const [userData, setUserData] = useState({
    username: "",
    handle: "",
    biography: "",
    profilePicture: DefaultProfilePicture,
    profileBanner: DefaultBanner,
    friendCount: 0,
  });
  const [posts, setPosts] = useState([]);
  const [error, setError] = useState("");

  const [showModal, setShowModal] = useState(false);

  const closeModal = () => {
    setShowModal(false);
  };
  const openModal = () => {
    setShowModal(true);
  };

  const [buttonType, setButtonType] = useState(-1);

  const token = localStorage.getItem("authToken");
  const { userId: userIdFromParams } = useParams();
  const userIdFromToken = parseJwt(token);
  const userIdToDisplay = userIdFromParams || userIdFromToken;

  const addFriend = async () => {
    await createFriendRequest(userIdToDisplay, token);
  };

  const findIdByFromId = (data, fromId) => {
    const foundElement = data.find((element) => element.from.id === fromId);

    return foundElement ? foundElement.id : null;
  };

  const removeFriend = async () => {
    try {
      const userIdFromToken = parseInt(parseJwt(token).sub);
      //Find ID of friending
      let url = `/api/friends?from=${userIdFromToken}`;
      let response = await fetch(url, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });

      let result = await response.json();
      console.log(result.data);
      let friendID = -1;
      let friendRequestList = result.data;
      if (response.ok && result.status === "success") {
        friendID = findIdByFromId(result.data, userIdFromToken);
        if (friendID !== -1) {
          console.log("got friend entry ID: " + friendID);
        } else {
          return "Fail";
        }
      } else {
        console.log("couldn't get ID of friend entry");
        return "Fail";
      }

      //Delete friends
      url = `/api/friends/${friendID}`;

      response = await fetch(url, {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });
      console.log("response ok: " + response.ok);

      if (response.ok && result.status === "success") {
        console.log("worked!");
        console.log(result.data);
      }
    } catch (err) {
      console.error("error removing friend request:", err);
      return "Failed";
    }
  };

  const submitNewUserData = async ({ image, displayName, biography }) => {
    try {
      const data = {
        displayName: displayName,
        biography: biography,
        profilePicture: image,
      };
      console.log(data.displayName + "\n" + data.biography);
      await updateUserData(userIdFromToken, token, data);
      await fetchData();
    } catch (error) {
      console.error("Error updating user data:", error);
      setError("Error updating user data");
    }
  };

  const fetchData = async () => {
    if (!token || !userIdToDisplay) {
      setError("User is not authenticated");
      return;
    }

    try {
      const { userData: userResponse, userPosts } = await fetchUserAndPosts(
        userIdToDisplay,
        token
      );

      const friendData = await getFriendData(userIdToDisplay, token);

      setUserData({
        username: userResponse.data.username || "user",
        displayName: userResponse.data.displayName,
        handle: userResponse.data.username || "handle",
        biography: userResponse.data.biography,
        profilePicture:
          userResponse.data.profilePicture || DefaultProfilePicture,
        profileBanner: userResponse.data.profileBanner || DefaultBanner,
        friendCount: Object.keys(friendData).length,
      });

      const friendsWithThisUser = friendData.some(
        (friend) => friend.id === userIdFromToken
      );

      //Set shown button (0 = add friend, 1 = remove friend, 2 = edit profile)
      if (userIdToDisplay === userIdFromToken) {
        setButtonType(2);
      } else if (friendsWithThisUser) {
        setButtonType(1);
      } else {
        setButtonType(0);
      }

      setPosts(userPosts);
    } catch (error) {
      console.error("Error fetching data:", error);
      setError("Error loading user data");
    }
  };

  useEffect(() => {
    fetchData();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [userIdToDisplay, token]);

  return {
    userData,
    posts,
    error,
    fetchData,
    userIdToDisplay,
    token,
    submitNewUserData,
    showModal,
    closeModal,
    buttonType,
    openModal,
    addFriend,
    removeFriend,
  };
};

export default useProfileData;
