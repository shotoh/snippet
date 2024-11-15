/**
 * Retrieve all posts from the server
 */
export async function fetchPosts(token) {
  if (!token) {
    throw new Error("No token provided");
  }

  try {
    const response = await fetch("/api/posts", {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    if (!response.ok) {
      throw new Error("Failed to retrieve posts");
    }

    const result = await response.json();

    if (response.ok && result.status === "success") {
      result.forEach((post) => {
        getFullPost(post.id, token);
      });
      return result.data;
    } else {
      throw new Error("Error fetching posts");
    }
  } catch (error) {
    console.error(error);
  }
}

/**
 * Retrieve a post object along with additional details
 * @returns - { user: { name, profilePicture }, media, text, likes, dislikes, comments }
 */
export async function getFullPost(postID, token) {
  const fullPost = {
    id: null,
    user: { userID: 0, name: "", profilePicture: null },
    media: null,
    text: "",
    likes: 0,
    dislikes: 0,
    comments: [],
  };

  if (!token) {
    throw new Error("No token provided");
  }

  // ** (Maybe update?) { id, user, content }
  const response = await fetch(`/api/posts/${postID}`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  if (!response.ok) {
    throw new Error("Failed to retrieve post");
  }

  const result = await response.json();

  fullPost.id = result.data.id;
  fullPost.user.userID = result.data.user.id;
  fullPost.user.name = result.data.user.username; // ** Replace with displayName when implemented
  // **   fullPost.user.profilePicture =

  fullPost.media = result.data.images;
  fullPost.text = result.data.content;

  // { likes, dislikes }
  // const ratings = await getPostRatings(postID, token);
  fullPost.likes = 0;
  fullPost.dislikes = 0;

  // { comments }
  const comments = await getPostComments(postID, token);
  fullPost.comments = comments;

  return fullPost;
}

/**
 * Retrieve the likes and dislikes of a post
 * @returns - { likes, dislikes }
 */
export async function getPostRatings(postID, token) {
  const postRating = {
    likes: 0,
    dislikes: 0,
  };

  if (!token) {
    throw new Error("No token provided");
  }

  try {
    // Get post likes
    const likesResponse = await fetch(`/api/post-likes?post=${postID}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (likesResponse.ok) {
      const result = await likesResponse.json();
      if (result.status === "success") {
        postRating.likes = result.data.length;
      }
    } else {
      console.error("Failed to fetch likes");
    }
  } catch (error) {
    console.error("Error fetching likes:", error);
  }

  return postRating;
}

/**
 * Retrieve all comments for a post
 */
export async function getPostComments(postID, token) {
  if (!token) {
    throw new Error("No token provided");
  }

  const response = await fetch(`/api/comments?post=${postID}`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  const result = await response.json();
  return result.data;
}

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
        Authorization: `Bearer ${token}`
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
        Authorization: `Bearer ${token}`
      },
    });

    if (response.status === 204) {
      return response.status;
    } else {
      const errorText = await response.text();
      throw new Error(`Failed to unlike posts: ${response.status} - ${errorText}`);
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
        Authorization: `Bearer ${token}`
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