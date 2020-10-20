import React from 'react';
import './App.css';
import PersistentDrawerLeft from './components/Drawer';
import Home from './components/Home';
import Human from './components/Human';
import Department from './components/Department';
import Manage from './components/Manage';
import { BrowserRouter, Route } from 'react-router-dom';
import { Container, CssBaseline, Grid } from '@material-ui/core';


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
