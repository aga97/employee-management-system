import { Chip, CircularProgress, makeStyles, Paper } from '@material-ui/core';
import axios from 'axios';
import React, { useEffect, useState } from 'react';

const useStyles = makeStyles((theme) => ({
 root: {
   backgroundColor : theme.palette.background.default,
 },
 paper: {
  padding : theme.spacing(1),
  marginBottom : theme.spacing(1),
 },
 retire: {
  padding : theme.spacing(1),
  marginTop : theme.spacing(1),
  marginBottom : theme.spacing(1),
 },
 header: {
   backgroundColor : theme.palette.grey[400]
 }
}));


function Revise(props) {

  const [datas, setDatas ] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const classes = useStyles();
  
  useEffect(() => {
    let unmounted = false;
    const fetchDatas = async () => {      
      try {           
        setDatas(null);
        setLoading(true);        
        const res = await axios.get('http://localhost:8080/api/employee/' + props.empNo )
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
  },[props.empNo])
  if(loading) {
    return(
    <div >
      <CircularProgress color="secondary" />        
    </div>
    )
  }
  if(error) {
    return <div>error</div>
  }
  if (!datas) return null;

  return (
    <Paper className={classes.root} >
      {datas.content.retirement && 
      <Chip className={classes.retire} label="은퇴함" color="secondary"/>}
      <Paper className={classes.paper} elevation={0}>
        직원 번호 : {datas.content.empNo}
      </Paper>      
      <Paper className={classes.paper} elevation={0}>
        이름 : {datas.content.firstName}
      </Paper>
      <Paper className={classes.paper} elevation={0}>
        성 : {datas.content.lastName}
      </Paper>
      <Paper className={classes.paper} elevation={0}>
        생년월일 : {datas.content.birthDate}
      </Paper>
      <Paper className={classes.paper} elevation={0}>
        성별 : {datas.content.gender}
      </Paper>
      <Paper className={classes.paper} elevation={0}>
        고용일 : {datas.content.hireDate}
      </Paper>
      <Paper className={classes.paper} elevation={0}>
        부서 : {datas.content.deptName}
      </Paper>
      <Paper className={classes.paper} elevation={0}>
        직책 : {datas.content.title}
      </Paper>
      <Paper className={classes.paper} elevation={0}>
        연봉 : {datas.content.salary}
      </Paper>
    </Paper>
  )
}

export default Revise;