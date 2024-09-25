import React, { useState } from "react";
import { PlusIcon } from "@heroicons/react/24/solid";
import companyLogo from "../../images/macrosoftLogo.png";

export default function ProfilePage() {
  const [image, setImage] = useState(null);
  const [error, setError] = useState(null);

  const handleUpload = (event) => {
    const file = event.target.files?.[0];
    if (file && file.type.startsWith("image/")) {
      setImage(URL.createObjectURL(file));
      setError(null);
    } else {
      setError("Invalid file type. Please upload an image.");
      setImage(null);
    }
  };

  // User wants to upload profile picture later
  const handleDoLater = () => {
    // Redirect to another page
    /* (TO DO) */
  };

  // Save profile picture
  const handleConfirm = () => {
    // Save image to db
    /* (TO DO) */
    // Redirect to another page
    /* (TO DO) */
  };

  return (
    <div
      className="fixed inset-0 flex justify-center items-center bg-white"
      style={{ minWidth: "100vw", minHeight: "100vh", overflow: "hidden" }}
    >
      <div
        className="bg-gradient-to-tr from-pink-300 via-purple-300 to-indigo-400 shadow-lg text-center overflow-hidden"
        style={{ width: 700, height: 900, position: "relative" }}
      >
        <img
          src={companyLogo}
          alt="Macrosoft Logo"
          className="absolute top-6 left-6 w-24 h-24"
        />
        <div className="pt-36 pb-12 px-6">
          <div
            className="w-72 h-72 rounded-full mx-auto transition ease-in-out bg-slate-50 border-1 border-gray-500 cursor-pointer flex justify-center items-center"
            onClick={() => document.getElementById("fileInput").click()}
          >
            {image ? (
              <img
                src={image}
                alt="profile pic"
                className="w-full h-full rounded-full object-cover"
              />
            ) : (
              <PlusIcon className="w-12 h-12 text-gray-600" />
            )}
          </div>
          <input
            type="file"
            id="fileInput"
            onChange={handleUpload}
            style={{ display: "none" }}
            accept="image/*"
          />
        </div>
        <h1 className="text-4xl font-normal">Upload your</h1>
        <h1 className="text-6xl font-bold mb-8">Profile Picture</h1>
        {error && <p className="text-red-500 text-lg">{error}</p>}
        {image && (
          <button
            onClick={handleConfirm}
            className="transition ease-in-out bg-sky-500 hover:bg-sky-600 text-white py-2 px-8 rounded text-xl"
          >
            Looks good!
          </button>
        )}
        <button
          className="absolute bottom-4 right-4 transition ease-in-out hover:backdrop-brightness-90 py-2 px-8 rounded text-xl"
          onClick={handleDoLater}
        >
          Do this later →
        </button>
      </div>
    </div>
  );
}
