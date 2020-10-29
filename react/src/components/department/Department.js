import React, { useEffect, useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { useDispatch } from 'react-redux';
import * as actions from '../../actions'
import axios from 'axios';
import { CircularProgress } from '@material-ui/core';
import DepInside from './DepInside'
import Accordion from '@material-ui/core/Accordion';
import AccordionSummary from '@material-ui/core/AccordionSummary';
import AccordionDetails from '@material-ui/core/AccordionDetails';
import Typography from '@material-ui/core/Typography';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,   
  },
  paper: {    
    textAlign: 'center',
    color: theme.palette.text.primary,   
    fontWeight: "bold",
  },
  link: {
    textDecoration: 'none',
  },
  progress: {
    marginTop: theme.spacing(25), 
  }
}));

export default function Department() {
  const dispatch = useDispatch();
  const classes = useStyles();

  // const { dep } = useSelector((state) => ({
  //   dep:state.asd,
  // }))

  //Data, Loading, Error
  const [datas, setDatas ] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    dispatch(actions.navigation(2));

    let unmounted = false;
    
    const fetchDatas = async () => {      
      try {           
        setDatas(null);
        setLoading(true);        
        const res = await axios.get('http://localhost:8080/api/departments');
        if(!unmounted)
          setDatas(res.data);          
      } catch (e) {        
        setError(e);
      }
      if(!unmounted) setLoading(false);            
    };
    fetchDatas();
    return () => {//clean up      
      unmounted = true;
    }    
  },[dispatch])

  if(loading) {
      return(
      <div >
        <CircularProgress className={classes.progress}  color="secondary" size={100}/>        
      </div>
    )
  }
  if(error) {
    return <div>error</div>
  }
  if (!datas) return null;

 
  return (
    <div>          
      {datas.content.map((text) => (
        <Accordion TransitionProps={{ unmountOnExit: true }} key={text.deptNo}>
        <AccordionSummary
          expandIcon={<ExpandMoreIcon />}
          aria-controls={text.deptName}
          id={text.deptNo}
        >
          <Typography className={classes.paper}>{text.deptName}</Typography>
        </AccordionSummary>
        <AccordionDetails>
          <DepInside deptNo={text.deptNo} />
        </AccordionDetails>
      </Accordion>
        ))}      
    </div>
  );
}
