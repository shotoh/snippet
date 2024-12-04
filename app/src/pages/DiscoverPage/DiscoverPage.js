import React, { useState, useEffect } from "react";
import NavBar from "../../components/MainPage/NavBar";
import { InputGroup, Form } from "react-bootstrap";
import { fetchDiscoverPosts, fetchAllUsers } from "../../api/DiscoverAPI";
import DiscoverFeed from "../../components/DiscoverPage/DiscoverFeed";
import { getPostsfromUser, redirectToUser } from "../../hooks/useDiscoverData";

export default function DiscoverPage() {
  const [posts, setPosts] = useState([]);
  const [error, setError] = useState("");
  const [input, setInput] = useState("");

  useEffect(() => {
    loadDiscoverPosts();
  }, []);

  const loadDiscoverPosts = async (searchTerm = "") => {
    try {
      if (searchTerm) {
        const postData = await getPostsfromUser(searchTerm);
        console.log("Fetched discover post data: ", postData);
        setPosts(postData);
        return;
      }

      const postData = await fetchDiscoverPosts();
      console.log("Fetched discover post data: ", postData);
      setPosts(postData);
    } catch (error) {
      console.error(error);
      setError("Error loading discover posts");
    }
  };

  const goToUser = async (search) => {
    try {
      await redirectToUser(search);
    } catch (error) {
      console.error(error);
      setError("Error going to user");
    }
  }

  const handleInputChange = () => {
    goToUser(input);
  };

  return (
    <div className="min-h-screen bg-slate-200 flex flex-col">
      {/* Navbar */}
      <NavBar onPostCreated={loadDiscoverPosts} />

      <div className="flex-grow flex justify-center mt-4">
        <div className="container max-w-5xl bg-sky-500 p-4 rounded-lg">
          <InputGroup className="mb-4">
            <Form.Control
              placeholder="Find your next inspiration!"
              aria-label="Search Inspiration"
              value={input}
              onChange={(e) => setInput(e.target.value)} // Update input state on change
              onKeyDown={(e) => {
                if (e.key === "Enter") {
                  handleInputChange();
                }
              }}
            />
            <InputGroup.Text>
              <i className="bi bi-search"></i>
            </InputGroup.Text>
          </InputGroup>

          <div className="space-y-4">
            <DiscoverFeed
              posts={posts}
              loadDiscoverPosts={loadDiscoverPosts}
              error={error}
            />
          </div>
        </div>
      </div>
    </div>
  );
}