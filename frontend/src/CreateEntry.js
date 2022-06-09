import React, { useEffect, useState } from "react";

export default function CreateEntry() {
  const [name, setName] = useState("");
  const [message, setMessage] = useState("");
  let handleSubmit = async (e) => {
    e.preventDefault();
    try {
      let res = await fetch('http://localhost:8080/api/user', {
        method: "POST",
        body: JSON.stringify({
          name: name
        }),
        headers: {
          'Content-Type': 'application/json',
        }
      });
      let resJson = await res.json();
      if (res.status === 201) {
        setName("");
        setMessage("User created successfully");
      } else {
        setMessage("Some error occured");
      }
    } catch (err) {
      console.log(err);
    }
  };
  return(
    <div>
      <h3>POST - Create a new User</h3>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          value={name}
          placeholder="Name"
          onChange={(e) => setName(e.target.value)}></input>
        <button type="submit">Create</button>
        <div className="message">{message ? <p>{message}</p> : null}</div>        
      </form>
    </div>
  );
}