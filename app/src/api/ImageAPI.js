/**
 * Convert an image to a base64 string
 */
export const imageToBase64 = async (image) => {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(image);
    reader.onload = () => resolve(reader.result);
    reader.onerror = (error) => reject(error);
  });
};

/**
 * Upload an image for a post
 */
export const uploadPostImage = async (image, postId, userId, token) => {
  const base64Image = await imageToBase64(image);

  try {
    const response = await fetch(`/api/images`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({
        content: base64Image,
        postId: postId,
        userId: userId,
      }),
    });

    if (!response.ok) {
      throw new Error("Failed to upload image");
    }
  } catch (err) {
    console.error(err);
    throw err; // Re-throw the error to be caught in handleCreatePost
  }
};

/**
 * Retrieve an image for a post
 */
export const getPostImage = async (postId, token) => {
  if (!token) {
    throw new Error("No token provided");
  }

  try {
    const response = await fetch(`/api/images?post=${postId}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (response.ok) {
      const result = await response.json();
      console.log("[result]", result);
      if (result.status === "success") {
        if (result.data.length !== 0) {
          return result.data[0].content; // ** Returns a list of images, taking the first one for now
        }
      }
    } else {
      console.error("Failed to fetch image");
    }
  } catch (error) {
    console.error("Error fetching image:", error);
  }

  return null;
};

/**
 * Retrieve all images
 */
