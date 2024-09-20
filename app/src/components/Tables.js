import React from "react";

// Helper function to get nested properties from an object
const getNestedValue = (obj, key) => {
  return key.split(".").reduce((acc, part) => acc && acc[part], obj);
};

// A generic DataTable component for all the tables
export const DataTable = ({ data, columns }) => (
  <div className="relative overflow-hidden rounded-md shadow-md shadow-black/20">
    <table className="table-auto border-collapse bg-white rounded-md overflow-hidden">
      <thead>
        <tr>
          {columns.map((col) => (
            <th
              key={col.key}
              className="border border-gray-900 px-4 py-1.5 bg-lime-100 uppercase"
            >
              {col.title}
            </th>
          ))}
        </tr>
      </thead>
      <tbody>
        {data.map((item, index) => (
          <tr key={index} className="odd:bg-white even:bg-slate-100">
            {columns.map((col) => (
              <td key={col.key} className="border border-gray-900 px-4 py-1.5">
                {getNestedValue(item, col.key) || "N/A"}
              </td>
            ))}
          </tr>
        ))}
      </tbody>
    </table>
  </div>
);

// Define column structures for each table
export const UserTable = ({ data }) => {
  const columns = [
    { key: "id", title: "ID" },
    { key: "username", title: "Username" },
    { key: "email", title: "Email" },
  ];
  return <DataTable data={data} columns={columns} />;
};

export const PostTable = ({ data }) => {
  const columns = [
    { key: "id", title: "ID" },
    { key: "title", title: "Title" },
    { key: "content", title: "Content" },
    { key: "user.id", title: "User ID" }, // Accessing nested property 'user.id'
  ];
  return <DataTable data={data} columns={columns} />;
};

export const CommentTable = ({ data }) => {
  const columns = [
    { key: "id", title: "ID" },
    { key: "content", title: "Content" },
    { key: "user.id", title: "User ID" },
    { key: "post.id", title: "Post ID" },
  ];
  return <DataTable data={data} columns={columns} />;
};

export const PostLikesTable = ({ data }) => {
  const columns = [
    { key: "post.id", title: "Post ID" }, // Accessing nested property 'post.id'
    { key: "user.id", title: "User ID" },
    { key: "id", title: "ID" },
  ];
  return <DataTable data={data} columns={columns} />;
};

export const CommentLikesTable = ({ data }) => {
  const columns = [
    { key: "comment.id", title: "Comment ID" }, // Accessing nested property 'comment.id'
    { key: "user.id", title: "User ID" },
    { key: "id", title: "ID" },
  ];
  return <DataTable data={data} columns={columns} />;
};

export const FriendsTable = ({ data }) => {
  const columns = [
    { key: "from.id", title: "From ID" }, // Accessing nested property 'user.id'
    { key: "to.id", title: "To ID" },
    { key: "id", title: "ID" },
    { key: "status", title: "Status" },
  ];
  return <DataTable data={data} columns={columns} />;
};

export const MessagesTable = ({ data }) => {
  const columns = [
    { key: "id", title: "ID" },
    { key: "content", title: "Content" },
    { key: "from.id", title: "From ID" },
    { key: "to.id", title: "To ID" },
  ];
  return <DataTable data={data} columns={columns} />;
};

export const MediaTable = ({ data }) => {
  const columns = [
    { key: "post.id", title: "Post ID" }, // Accessing nested property 'post.id'
    { key: "source", title: "Source" },
    { key: "id", title: "ID" },
  ];
  return <DataTable data={data} columns={columns} />;
};
