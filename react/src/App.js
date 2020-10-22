import React from 'react';
import './App.css';
import PersistentDrawerLeft from './components/Drawer';
import { BrowserRouter, Route } from 'react-router-dom';


function App() {
  return (        
      
    <div className="App">            
        <BrowserRouter>         
        <Route component={PersistentDrawerLeft} />           
        </BrowserRouter> 
    </div>   
    
  );
}

export default App;
