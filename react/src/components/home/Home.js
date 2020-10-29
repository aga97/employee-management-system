import React, { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import Posts from './Posts';
import * as actions from '../../actions'

function Home(props){
    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(actions.navigation(0));
    },[dispatch])


    return (
        <div>              
            <Posts/>               
        </div>
    
    );
}
export default Home;