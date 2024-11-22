import defaultProfile from "../../images/defaultprofile.png";
import React, { useState, useEffect } from "react";
import { FaThumbsUp, FaTimes } from "react-icons/fa";
import { getUserData } from "../../api/ProfileAPI";
import { getImage } from "../../api/ImageAPI";

export default function CommentsPopup({ postId, onClose, updateCommentCount }) {
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState("");
  const [error, setError] = useState("");
  const [user, setUser] = useState(null); //store user info
  const token = localStorage.getItem("authToken");

  // Fetch the logged-in user's details
  useEffect(() => {
    const fetchUserDetails = async () => {
      try {
        const userId = JSON.parse(atob(token.split(".")[1])).sub; 
        const userData = await getUserData(userId, token);
        const profilePictureUrl = userData.profilePicture
          ? await getImage(userData.profilePicture, token)
          : defaultProfile;

        setUser({
          username: userData.username,
          profilePicture: profilePictureUrl,
        });
      } catch (err) {
        setError("Failed to fetch user details");
      }
    };

    fetchUserDetails();
  }, [token]);

  const fetchComments = async () => {
    try {
      const response = await fetch(`/api/comments?post=${postId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      const result = await response.json();
      if (response.ok && result.status === "success") {
        const updatedComments = await Promise.all(
          result.data.map(async (comment) => {
            const userData = await getUserData(comment.user.id, token);
            const profilePictureUrl = userData.profilePicture
              ? await getImage(userData.profilePicture, token)
              : defaultProfile;
            return {
              ...comment,
              user: {
                ...comment.user,
                profilePicture: profilePictureUrl,
              },
            };
          })
        );
        setComments(updatedComments);
        updateCommentCount(updatedComments.length); 
      } else {
        throw new Error(result.message || "Failed to load comments");
      }
    } catch (err) {
      setError(err.message);
    }
  };

  useEffect(() => {
    fetchComments();
  }, [postId]);

  const handleAddComment = async () => {
    if (!newComment.trim()) return;

    try {
      const response = await fetch("/api/comments", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          postId,
          content: newComment,
        }),
      });

      if (response.ok) {
        setNewComment(""); 
        fetchComments(); 
      } else {
        throw new Error("Failed to add comment");
      }
    } catch (err) {
      setError(err.message);
    }
  };

  const handleLikeComment = async (commentId) => {
    try {
      const response = await fetch(`/api/comments/${commentId}/like`, {
        method: "PATCH",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (response.ok) {
        setComments((prev) =>
          prev.map((comment) =>
            comment.id === commentId
              ? { ...comment, likes: comment.likes + 1 }
              : comment
          )
        );
      } else {
        throw new Error("Failed to like comment");
      }
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
      <div className="bg-white rounded-lg w-96 max-h-[90vh] overflow-auto shadow-lg relative p-4">
        <button
          className="absolute top-2 right-2 text-gray-500 hover:text-gray-700"
          onClick={onClose}
        >
          <FaTimes className="w-5 h-5" />
        </button>

        <h2 className="text-lg font-bold mb-4">Comments</h2>

        {/* New comment input */}
        <div className="mb-4">
          <div className="flex items-center mb-2">
            <img
              src={user?.profilePicture || defaultProfile}
              alt="User Avatar"
              className="w-10 h-10 rounded-full object-cover mr-3"
            />
            <textarea
              className="w-full border rounded p-2"
              placeholder="Write a comment..."
              value={newComment}
              onChange={(e) => setNewComment(e.target.value)}
            />
          </div>
          <button
            className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
            onClick={handleAddComment}
          >
            Submit
          </button>
        </div>

        {error && <p className="text-red-500">{error}</p>}

        {/* Comments */}
        <ul className="space-y-4">
          {comments.map((comment) => (
            <li key={comment.id} className="border-b pb-2">
              <div className="flex items-center mb-2">
                <img
                  src={comment.user?.profilePicture || defaultProfile}
                  alt={`${comment.user?.username || "User"}'s Avatar`}
                  className="w-10 h-10 rounded-full object-cover mr-3"
                />
                <div>
                  <p className="font-semibold text-sm">
                    {comment.user?.username || "Unknown"}
                  </p>
                  <p className="text-sm text-gray-700">{comment.content}</p>
                </div>
              </div>
              <div className="flex items-center justify-end text-gray-500 text-xs">
                <button
                  className="flex items-center space-x-1"
                  onClick={() => handleLikeComment(comment.id)}
                >
                  <FaThumbsUp />
                  <span>{comment.likes}</span>
                </button>
              </div>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}