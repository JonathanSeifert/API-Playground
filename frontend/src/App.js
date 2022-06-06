import React, { useEffect, useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import axios from 'axios';
import EntryList from './EntryList';
import CreateEntry from './CreateEntry';

const App = () => {
  return (
    <><div><EntryList /></div>
    <div><CreateEntry /></div></>
  )
}
export default App;
