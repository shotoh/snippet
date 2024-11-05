/**
 * Upload an image for a post
 */
export const uploadPostImage = async (image, postId, token) => {
  const formData = new FormData();
  formData.append("file", image);

  try {
    const response = await fetch(`/api/posts/${postId}/picture`, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
      },
      body: formData,
    });

    if (!response.ok) {
      throw new Error("Failed to upload image");
    }
  } catch (err) {
    console.error(err);
    throw err;
  }
};

/**
 * Retrieve an image
 */
export const getImage = async (filename, token) => {
  try {
    const response = await fetch(`/api/images/${filename}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (!response.ok) {
      throw new Error("Failed to fetch image");
    }

    const blob = await response.blob();
    return URL.createObjectURL(blob);
  } catch (err) {
    console.error(err);
    return null;
  }
};
