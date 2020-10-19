import React from 'react';
import logo from './logo.svg';
import './App.css';
import HeaderBar from './components/HeaderBar';
import SimpleList from './components/SimpleList';
import Posts from './components/Posts';


function App() {
  return (    
    <div className="App">
      <HeaderBar/>
        <div style={{display: 'flex'}}>
          <SimpleList/>
          <Posts/>        
        </div>
    </div>    
  );
}

export default App;
