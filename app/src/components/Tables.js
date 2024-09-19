import React from "react";

export const UserTable = ({ data, headers }) => (
  <table className="table-auto border-collapse border border-white mt-4">
    <thead>
      <tr>
        {headers.map((header) => (
          <th key={header} className="border border-white px-4 py-2">
            {header.charAt(0).toUpperCase() + header.slice(1)}
          </th>
        ))}
      </tr>
    </thead>
    <tbody>
      {data.map((item, index) => (
        <tr key={index}>
          {headers.map((header) => (
            <td key={header} className="border border-white px-4 py-2">
              {item[header]}
            </td>
          ))}
        </tr>
      ))}
    </tbody>
  </table>
);

export const PostTable = ({ data, headers }) => (
    <table className="table-auto border-collapse border border-white mt-4">
      <thead>
        <tr>
          {headers.map((header) => (
            <th key={header} className="border border-white px-4 py-2">
              {header.charAt(0).toUpperCase() + header.slice(1)}
            </th>
          ))}
        </tr>
      </thead>
      <tbody>
        {data.map((item, index) => (
          <tr key={index}>
            {headers.map((header) => {
              const cellData = item[header]; 
  
              return (
                <td key={header} className="border border-white px-4 py-2">
                  {header === "user_id" && typeof cellData === "object"
                    ? cellData.id || 'Unknown' 
                    : typeof cellData === "object"
                    ? JSON.stringify(cellData) 
                    : cellData} 
                </td>
              );
            })}
          </tr>
        ))}
      </tbody>
    </table>
  );

export const CommentTable = ({ data, headers }) => {
  return (
    <table className="table-auto border-collapse border border-white mt-4">
      <thead>
        <tr>
          {headers.map((header) => (
            <th key={header} className="border border-white px-4 py-2">
              {header.charAt(0).toUpperCase() + header.slice(1)}
            </th>
          ))}
        </tr>
      </thead>
      <tbody>
        {data.map((item, index) => (
          <tr key={index}>
            {headers.map((header) => (
              <td key={header} className="border border-white px-4 py-2">
                {item[header]}
              </td>
            ))}
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export const PostLikesTable = ({ data, headers }) => (
  <table className="table-auto border-collapse border border-white mt-4">
    <thead>
      <tr>
        {headers.map((header) => (
          <th key={header} className="border border-white px-4 py-2">
            {header.charAt(0).toUpperCase() + header.slice(1)}
          </th>
        ))}
      </tr>
    </thead>
    <tbody>
      {data.map((item, index) => (
        <tr key={index}>
          {headers.map((header) => (
            <td key={header} className="border border-white px-4 py-2">
              {item[header]}
            </td>
          ))}
        </tr>
      ))}
    </tbody>
  </table>
);

export const CommentLikesTable = ({ data, headers }) => (
  <table className="table-auto border-collapse border border-white mt-4">
    <thead>
      <tr>
        {headers.map((header) => (
          <th key={header} className="border border-white px-4 py-2">
            {header.charAt(0).toUpperCase() + header.slice(1)}
          </th>
        ))}
      </tr>
    </thead>
    <tbody>
      {data.map((item, index) => (
        <tr key={index}>
          {headers.map((header) => (
            <td key={header} className="border border-white px-4 py-2">
              {item[header]}
            </td>
          ))}
        </tr>
      ))}
    </tbody>
  </table>
);

export const FriendsTable = ({ data, headers }) => (
  <table className="table-auto border-collapse border border-white mt-4">
    <thead>
      <tr>
        {headers.map((header) => (
          <th key={header} className="border border-white px-4 py-2">
            {header.charAt(0).toUpperCase() + header.slice(1)}
          </th>
        ))}
      </tr>
    </thead>
    <tbody>
      {data.map((item, index) => (
        <tr key={index}>
          {headers.map((header) => (
            <td key={header} className="border border-white px-4 py-2">
              {item[header]}
            </td>
          ))}
        </tr>
      ))}
    </tbody>
  </table>
);

export const MessagesTable = ({ data, headers }) => (
  <table className="table-auto border-collapse border border-white mt-4">
    <thead>
      <tr>
        {headers.map((header) => (
          <th key={header} className="border border-white px-4 py-2">
            {header.charAt(0).toUpperCase() + header.slice(1)}
          </th>
        ))}
      </tr>
    </thead>
    <tbody>
      {data.map((item, index) => (
        <tr key={index}>
          {headers.map((header) => (
            <td key={header} className="border border-white px-4 py-2">
              {item[header]}
            </td>
          ))}
        </tr>
      ))}
    </tbody>
  </table>
);

export const MediaTable = ({ data, headers }) => (
    <table className="table-auto border-collapse border border-white mt-4">
      <thead>
        <tr>
          {headers.map((header) => (
            <th key={header} className="border border-white px-4 py-2">
              {header.charAt(0).toUpperCase() + header.slice(1)}
            </th>
          ))}
        </tr>
      </thead>
      <tbody>
        {data.map((item, index) => (
          <tr key={index}>
            {headers.map((header) => {
              const cellData = item[header]; 
              return (
                <td key={header} className="border border-white px-4 py-2">
                  {typeof cellData === 'object'
                    ? JSON.stringify(cellData)
                    : cellData}
                </td>
              );
            })}
          </tr>
        ))}
      </tbody>
    </table>
  );