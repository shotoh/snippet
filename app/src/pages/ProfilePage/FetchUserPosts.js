export const fetchUserPosts = async (userId) => {
    const token = localStorage.getItem('authToken');
    if (!token) {
      throw new Error("User is not authenticated");
    }
  
    try {
      const response = await fetch(`/api/posts?user=${userId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      const result = await response.json();
  
      if (response.ok && result.status === "success") {
        return result.data; 
      } else {
        throw new Error("Error fetching posts");
      }
    } catch (error) {
      console.error("Error fetching posts:", error);
      throw error;
    }
  };