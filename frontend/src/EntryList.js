import React, { useEffect, useState } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import { Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import axios from 'axios';


export default function EntryList() {
  const [data, setData] = useState([]);

  const fetchData = async() => {
    await fetch('http://localhost:8080/api/user')
      .then((res) => res.json())
      .then((res) => {
        console.log(res)
        setData(res)
      })
  }

  const handleDelete = async(id) => {
    const request = 'http://localhost:8080/api/user/' + id
    await fetch(request, {method: 'DELETE'})
  }
  
  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div>
      <h3>GET - Get all users</h3>
      <Table className='entries'>
        <thead>
          <tr>
            	<th width="5%">id</th>
            	<th width="20%">name</th>
          </tr>
        </thead>
        <tbody>
          {data.map(item => (
          <tr key={item.id}>
            <td>{item.id}</td>
            <td>{item.name}</td>
            <td><Button onClick={() => handleDelete(item.id)}>Delete</Button></td>
          </tr>
          ))}
        </tbody>
      </Table>
      <ul>
      </ul>
    </div>
  )
}

