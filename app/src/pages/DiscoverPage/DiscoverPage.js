import React, { useState, useEffect } from "react";
import NavBar from "../../components/MainPage/NavBar";
import { InputGroup, Form } from "react-bootstrap";
import { fetchDiscoverPosts } from "../../api/DiscoverAPI";
import DiscoverFeed from "../../components/DiscoverPage/DiscoverFeed";

export default function DiscoverPage() {
  const [posts, setPosts] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    loadDiscoverPosts();
  }, []);

  const loadDiscoverPosts = async () => {
    try {
      const postData = await fetchDiscoverPosts();
      console.log("Fetched discover post data: ", postData);
      setPosts(postData);
    } catch (error) {
      setError("Error loading discover posts");
    }
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