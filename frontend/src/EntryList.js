import React, { useEffect, useState } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import { Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import axios from 'axios';
import { Tab } from 'bootstrap';

export default function EntryList() {
  const [data, setData] = useState([]);
  useEffect(() => {
    const fetchData = async () => {
      try {
        const{data: response} = 
          await axios.get('http://localhost:8080/api/user');
          console.log(response);
          setData(response);
      } catch (error) {
        console.error(error.message);
      }
    }
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
          </tr>
          ))}
        </tbody>
      </Table>
      <ul>
      </ul>
    </div>
  )
}

