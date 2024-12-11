/**
 * Retrieve the token from localStorage
 */
export const getToken = () => localStorage.getItem("authToken");

/**
 * Helper function to parse JWT token
 */
export const parseJwt = (token) => {
  try {
    const base64Url = token.split(".")[1];
    const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
    return JSON.parse(window.atob(base64));
  } catch (error) {
    return null;
  }
};

/**
 * Fetch posts for discover page
 */
export const fetchDiscoverPosts = async () => {
    const token = getToken();
    if (!token) {
        throw new Error("User is not authenticated");
    }

    try {
        const response = await fetch("/api/posts/discover", {
            headers: { Authorization: `Bearer ${token}`},
        });
        const result = await response.json();

        if (response.ok && result.status === "success") {
            return result.data;
        } else {
            throw new Error("Error loading discover posts");
        }
    } catch (error) {
        console.error("Error loading discover posts: ", error);
        throw error;
    }
};


export const fetchAllUsers = async () => {
    const token = getToken();
    if (!token) {
        throw new Error("User is not authenticated");
    }

    try {
        const response = await fetch("/api/users", {
            method: "GET",
            headers: { Authorization: `Bearer ${token}`},
        });
        const result = await response.json();

        if (response.ok && result.status === "success") {
            //console.log(result.data);
            return result.data;
        } else {
            throw new Error("Error loading discover posts");
        }
    } catch (error) {
        console.error("Error loading discover posts: ", error);
        throw error;
    }
}