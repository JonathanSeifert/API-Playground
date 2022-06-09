import React, { useEffect, useState } from 'react';
import { Button, Table } from 'reactstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';


export default function EntryList() {
  const [data, setData] = useState([]);
  const [message, setMessage] = useState("");
  const [id, setId] = useState("");
  const [createName, setCreateName] = useState("");
  const [updateName, setUpdateName] = useState("");
  const [createMessage, setCreateMessage] = useState("");
  const [updateMessage, setUpdateMessage] = useState("");

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async() => {
    try {
      await fetch('http://localhost:8080/api/user')
        .then((res) => res.json())
        .then((res) => {
          setData(res)
          setMessage("")
        })
    } catch {
      setMessage("No users found.")
    }
  }

  const handleDelete = async(id) => {
    try {if (id === 'all') {
      const request = 'http://localhost:8080/api/user'
      await fetch(request, {method: 'DELETE'})
      fetchData();
      setData([]);
    } else {
      const request = 'http://localhost:8080/api/user/' + id
      await fetch(request, {method: 'DELETE'})
      fetchData()
// REFACTOR!!!
      if(data.length === 1) {
        setData([])
      }
    }
  } catch (err) {
    console.error(err)
  }
  }

  const handleCreate = async(e) => {
    e.preventDefault();
    try {
      const res = await fetch('http://localhost:8080/api/user', {
        method: "POST",
        body: JSON.stringify({
          name: createName
        }),
        headers: {
          'Content-Type': 'application/json',
        }
      });
      if (res.status === 201) {
        setCreateName("");
        setCreateMessage("User created successfully");
        setTimeout(() => {
          setCreateMessage("")
        }, 3000);
        fetchData();
      } else {
        setCreateMessage("Some error occured");
      }
    } catch (err) {
      console.log(err);
    }
  }

  const handleUpdate = async(e) => {
    try {
      const request = "http://localhost:8080/api/user/" + id
      const res = await fetch(request, {
        method: "PUT",
        body: JSON.stringify({
          name: updateName
        }),
        headers: {
          'Content-Type': 'application/json',
        }
      });
      if (res.status === 200) {
        setId("");
        setUpdateName("");
        setUpdateMessage("Edit successfull");
        console.log(updateName)
        setTimeout(() => {
          setUpdateMessage("")
        }, 30000);
      } else {
        setUpdateMessage("Some error occured");
      }
    } catch (err) {
      console.log(err);
    }
  }

  const handleSetUpdateForm = (id) => {
    const idEl = document.getElementById("update-id-el");
    idEl.value = id;
    setId(id);
  }
 
  return (
    <div>
      <div className="fetchData-group">
        <Button onClick={fetchData}>Reload</Button>
        <div id="message-el">{message}</div>
      </div>
      <Table className='entries'>
        <thead>
          <tr>
            	<th className="id-col" width="10%">ID</th>
            	<th className="name-col">Name</th>
              <th className="edit-col" width="10%"></th>
              <th className="delte-col" width="10%"><Button onClick={() => handleDelete('all')}>Delete</Button></th>
          </tr>
        </thead>
        <tbody>
          {data.length < 0 && <div>No Users found.</div>}
          {data.map(item => (
          <tr key={item.id}>
            <td className="id-col">{item.id}</td>
            <td className="name-col">{item.name}</td>
            <td className="edit-col"><Button onClick={() => handleSetUpdateForm(item.id)}>Edit</Button></td>
            <td className="delete-col"><Button onClick={() => handleDelete(item.id)}>Delete</Button></td>
          </tr>
          ))}
        </tbody>
      </Table>
      <div className="table-forms">
        <form className="create-entry-form" onSubmit={handleCreate}>
          <div>Create a new User</div>
          <input
            id="create-name-el"
            type="text"
            value={createName}
            placeholder="Name"
            onChange={(e) => setCreateName(e.target.value)}></input><br/>
          <Button className="form-btn" type="submit">Create</Button>
          <div className="message" id="create">{createMessage ? <p>{createMessage}</p> : null}</div>        
        </form>

        <form className="update-entry-form" onSubmit={handleUpdate}>
                <div>Update a User</div>
                <input
                  id="update-id-el"
                  type="text"
                  value={id}
                  placeholder="ID"
                  readOnly></input><br/>
                <input
                  id="update-name-el"
                  type="text"
                  value={updateName}
                  placeholder="Name"
                  onChange={(e) => setUpdateName(e.target.value)}></input><br/>
                <Button className="form-btn" type="submit">Edit</Button>
                <Button className="form-btn" onClick={(e) => {setId(""); setUpdateName("")}}>Clear</Button>
                <div className="message" id="update">{updateMessage ? <p>{updateMessage}</p> : null}</div>
        </form>
      </div>
    </div>
  )
}

