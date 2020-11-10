import React, { useEffect } from 'react';
import { useDispatch} from 'react-redux';
import { Route } from 'react-router-dom';
import Human from './Human';
import * as actions from '../../actions';

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