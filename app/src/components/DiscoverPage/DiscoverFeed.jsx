import React from "react";
import DiscoverCard from "./DiscoverCard";
import DefaultProfile from "../../images/defaultprofile.png";

export default function DiscoverFeed({ posts, error, loadDiscoverPosts }) {
    if (error) {
        return <p className="text-red-500">{error}</p>;
    }

    if (posts.length === 0) {
        return <p>No posts available</p>;
    }

    return (
        <div>
            {posts.map((post) => (
                <div key={post.id} className="py-3">
                    <DiscoverCard
                      discoverPost={{
                        id: post.id,
                        user: {
                            name: post.user?.username || "Unknown user",
                            profilePicture: post.user?.profilePicture || DefaultProfile,
                        },
                        media: post.images || [], //Default to empty array if post.images is undefined
                      }}
                      loadDiscoverPosts={loadDiscoverPosts}
                      />
                </div>
            ))}
        </div>
    );
}