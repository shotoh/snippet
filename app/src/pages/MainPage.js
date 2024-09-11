import React from 'react';
import { TopPosts, Feed, Friends } from '../components/Containers';

const MainPage = () => {
  return (
    <div className="main-page">
      <TopPosts />
      <Feed />
      <Friends />
    </div>
  );
};

export default MainPage;