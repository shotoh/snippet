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
    user: { name: "", profilePicture: null },
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
  fullPost.user.name = result.data.user.username; // ** Replace with displayName when implemented
  // **   fullPost.user.profilePicture =

  fullPost.media = result.data.images;
  fullPost.text = result.data.content;

  // { likes, dislikes }
  const ratings = await getPostRatings(postID, token);
  fullPost.likes = ratings.likes;
  fullPost.dislikes = ratings.dislikes;

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
