import { fetchDiscoverPosts, fetchAllUsers, getToken } from "../api/DiscoverAPI";
import { getUserPosts } from "../api/ProfileAPI";


let cachedUsers = null; // Cached users to avoid fetching repeatedly


export const getPostsfromUser = async (username) => {
    // Fetch users only if not already cached
    if (!cachedUsers) {
        cachedUsers = await fetchAllUsers();
    }

    if (!cachedUsers) {
        return;
    }
    // Create a list of usernames
    const usernames = cachedUsers.map(user => user.username);

    // Find the closest match to the input username
    const closestUsername = findClosestMatch(username, usernames);

    // Find and return the user object that matches the closest username
    const foundUser = cachedUsers.find(user => user.username === closestUsername);

    return await getUserPosts(foundUser.id, getToken());
}

export const redirectToUser = async (username) => {
    // Fetch users only if not already cached
    if (!cachedUsers) {
        cachedUsers = await fetchAllUsers();
    }

    if (!cachedUsers) {
        return;
    }
    // Create a list of usernames
    const usernames = cachedUsers.map(user => user.username);

    // Find the closest match to the input username
    const closestUsername = findClosestMatch(username, usernames);

    // Find and return the user object that matches the closest username
    const foundUser = cachedUsers.find(user => user.username === closestUsername);
    
    if (foundUser) {
        window.location.href = `/snippet/user/${foundUser.id}`;
    }
}

function findClosestMatch(inputString, list) {
    // Check for perfect matches
    for (let i = 0; i < list.length; i++) {
      if (inputString === list[i]) {
        return list[i];
      }
    }
  
    // If no perfect match found, use Levenshtein distance
    let closestMatch = null;
    let closestDistance = Infinity;
  
    for (let i = 0; i < list.length; i++) {
      const currentString = list[i];
      const currentDistance = levenshteinDistance(inputString, currentString);
  
      if (currentDistance < closestDistance) {
        closestMatch = currentString;
        closestDistance = currentDistance;
      }
    }
  
    return closestMatch;
  }
  
  
  // Levenshtein distance algorithm
  function levenshteinDistance(a, b) {
    if (a.length === 0) return b.length;
    if (b.length === 0) return a.length;
  
    const matrix = [];
  
    // Initialize the matrix
    for (let i = 0; i <= b.length; i++) {
      matrix[i] = [i];
    }
  
    for (let j = 0; j <= a.length; j++) {
      matrix[0][j] = j;
    }
  
    // Calculate distances
    for (let i = 1; i <= b.length; i++) {
      for (let j = 1; j <= a.length; j++) {
        if (b.charAt(i - 1) === a.charAt(j - 1)) {
          matrix[i][j] = matrix[i - 1][j - 1];
        } else {
          matrix[i][j] = Math.min(
            matrix[i - 1][j - 1] + 1, // Substitution
            matrix[i][j - 1] + 1, // Insertion
            matrix[i - 1][j] + 1 // Deletion
          );
        }
      }
    }
  
    return matrix[b.length][a.length];
  }