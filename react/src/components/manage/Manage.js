import React, { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import * as actions from '../../actions'

function Manage() {
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(actions.navigation(3));
  },[dispatch])


  return (
    <div>
      <h1>
        차후에 좀 더 생각해서 넣을 것
      </h1>
    </div>
  )
}

export default Manage;