import React from 'react';
import './App.css';
import PersistentDrawerLeft from './components/Drawer';


function App() {
  return (    
    <div className="App">
        <PersistentDrawerLeft/> 
        <div style={{display: 'flex'}}>                           
        </div>
    </div>    
  );
}

export default App;
