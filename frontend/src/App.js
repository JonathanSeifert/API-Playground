import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import EntryList from './EntryList';

const App = () => {
  return (
    <>
      <h1 className='title'>REST-Playground</h1>
      <div id="description-el">
        This is a simple Application to practice the use of REST-APIs. CRUD-Operations (Create, Read, Update and Delete) are supported.<br/>
        <i>Frontend:</i> 
        <ul>
          <li>React.js</li>
        </ul>
        <i>Backend:</i>
        <ul>
          <li>Java (Spring Boot)</li>
          <li>JPA</li>
          <li>PostgreSQL</li>
        </ul>
      </div>
      <EntryList />
    </>
  )
}
export default App;
