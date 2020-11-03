import React, { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import * as actions from '../../actions'
import Insert from './Insert';

function Manage() {
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(actions.navigation(3));
  },[dispatch])


  return (
    <div>
      <Insert />
    </div>
  )
}

export default Manage;