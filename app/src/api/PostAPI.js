/**
 * Handle liking a post
 */
export async function likePost(postID, token) {
  if (!token) {
    throw new Error("User is not authenticated");
  }

  try {
    const response = await fetch(`/api/posts/${postID}/like`, {
      method: "PATCH",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (response.status === 204) {
      return response.status;
    } else {
      const errorText = await response.text();
      throw new Error(`Failed to like post: ${response.status} - ${errorText}`);
    }
  } catch (error) {
    console.error("Error liking post: ", error);
    throw error;
  }
}

/**
 * Handle unliking a post if a user has already liked the post and presses like again
 */
export async function unlikePost(postID, token) {
  if (!token) {
    throw new Error("User is not authenticated");
  }

  try {
    const response = await fetch(`/api/posts/${postID}/like`, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (response.status === 204) {
      return response.status;
    } else {
      const errorText = await response.text();
      throw new Error(
        `Failed to unlike posts: ${response.status} - ${errorText}`
      );
    }
  } catch (error) {
    console.error("Error unliking post: ", error);
    throw error;
  }
}

/**
 * Handle disliking a post
 */
export async function dislikePost(postID, token) {
  if (!token) {
    throw new Error("User is not authenticated");
  }

  try {
    const response = await fetch(`/api/posts/${postID}/dislike`, {
      method: "PATCH",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (response.status === 204) {
      return response.status;
    } else {
      const errorText = await response.text();
      throw new Error(`Failed to like post: ${response.status} - ${errorText}`);
    }
  } catch (error) {
    console.error("Error disliking post: ", error);
    throw error;
  }
}
