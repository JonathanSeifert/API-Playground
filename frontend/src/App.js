import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import EntryList from './components/entryList/EntryList';

const App = () => {
  return (
    <>
      <h1 className='title'>API - Playground</h1>
      <EntryList />
      <div id="description-el">
        This is a simple application to practice the use of APIs, Spring Boot and React.js. CRUD-Operations (Create, Read, Update and Delete) are supported.<br/>
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
      
    </>
  )
}
export default App;
