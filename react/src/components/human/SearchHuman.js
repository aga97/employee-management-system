import { debounce, FormControl, InputLabel, makeStyles, Select, TextField } from '@material-ui/core';
import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Route } from 'react-router-dom';
import Human from './Human';
import * as actions from '../../actions';

const useStyles = makeStyles((theme) => ({   
    search: {
      '& > *': {
        margin: theme.spacing(2),  
        height: '6ch',      
        padding: '1px',   
      },
    },
  }));

function Home(props){

    const dispatch = useDispatch();

    useEffect(() => {                       
        dispatch(actions.navigation(1));

        return () => {//clean up    
            dispatch(actions.init());          
        }
        
      },[dispatch])
    


    return (      
        <div >      
            <Route component={Human}/> 
        </div>
     
    );
}
export default Home;