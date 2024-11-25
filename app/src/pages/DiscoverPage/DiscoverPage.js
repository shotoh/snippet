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
      <NavBar />  {/* add username display function */}

      <div className="container mx-auto mt-4 flex-grow grid grid-cols-12 gap-4 pr-4">
        {/* Discover Feed */}
        <div className="col-span-6 bg-sky-500 p-4 rounded-lg">
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
            <p>Posts will go here</p>
            < DiscoverFeed posts={posts} loadDiscoverPosts={loadDiscoverPosts} error={error} />
          </div>
        </div>        
      </div>
    </div>
  );
}