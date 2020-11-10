import { Button, Chip, CircularProgress, FormControl, Grid, InputLabel, makeStyles, Paper, Select, Snackbar, TextField } from '@material-ui/core';
import axios from 'axios';
import React, { useEffect, useState } from 'react';
import Delete from './Delete';
import MuiAlert from '@material-ui/lab/Alert';
import Push from './Push';

const useStyles = makeStyles((theme) => ({
 root: {
   backgroundColor : theme.palette.background.default,
   flexGrow: 1,
 },
 grid: {
  maxWidth: 250,
 },
 paper: {
  padding : theme.spacing(1),
 },
 retire: {
  padding : theme.spacing(1),
  marginTop : theme.spacing(1),
  marginBottom : theme.spacing(1),
 },
 filed: {
   padding : theme.spacing(1),
 },
 button: {
  marginTop : theme.spacing(1),
  marginBottom : theme.spacing(1),
  marginLeft : theme.spacing(1),
  float: 'right',
 }
}));

function Alert(props) {
  return <MuiAlert elevation={6} variant="filled" {...props} />;
}


function Editing(props) {

  const classes = useStyles();

  const [datas, setDatas ] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const [isPush, setIsPush] = useState(false);
  const [isDelete, setIsDelete] = useState(false);

  const [revise, setRevise] = useState({
    content :{
      empNo : '',
      deptNo : '',
      title : '',
      salary : 0
      },
      deptNo : false,
      title : false,
      salary : false,
  }
  );


  useEffect(() => {
    let unmounted = false;
    const fetchDatas = async () => {      
      try {           
        setDatas(null);
        setLoading(true);        
        const res = await axios.get('http://localhost:3000/api/employee/' + props.empNo )
        if(!unmounted){
          setDatas(res.data); 
          setRevise({
            content :{
              empNo : res.data.content.empNo,
              deptNo : res.data.content.deptNo,
              title : res.data.content.title,
              salary : res.data.content.salary
            },
            deptNo : false,
            title : false,
            salary : false,
          }
          )
        }
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

  const handleDeptChange = (e) => {
    if(e.target.value === datas.content.deptNo) {
      setRevise(
        {
          ...revise,
          deptNo : false,
        }
      )
    }
    else {
      setRevise(
        {
          ...revise,
          content: {
            ...revise.content,
            deptNo: e.target.value,
          },
          deptNo : true,
        }
      )
    }
  };

  const handleTitleChange = (e) => {
    if(e.target.value === datas.content.title) {
      setRevise(
        {
          ...revise,
          title : false,
        }
      )
    }
    else {
      setRevise(
        {
          ...revise,
          content : {
            ...revise.content,
            title: e.target.value,
          },
          title : true,
        }
      )
    }
  };

  const handleSalaryChange = (e) => {
    let numSalary = e.target.value * 1; 
    if(numSalary === datas.content.salary) {
      setRevise(
        {
          ...revise,
          salary : false,
        }
      )
    }
    else {
      setRevise(
        {
          ...revise,
          content: {
            ...revise.content,
            salary: numSalary,
          },
          salary : true,
        }
      )
    }
  };

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
    <div>
    <Paper className={classes.root} >
      {datas.content.retirement && 
      <Chip className={classes.retire} label="은퇴함" color="secondary"/>}
      <Grid container >
        <Grid item xs={6}>
          <Paper className={classes.paper}>
            <TextField className={classes.filed} label="사원 번호" value={datas.content.empNo} variant="outlined" disabled />
          </Paper>          
          <Paper className={classes.paper}>
            <TextField className={classes.filed} label="생년월일" value={datas.content.birthDate} variant="outlined" disabled/>
          </Paper>         
          <Paper className={classes.paper}>
            <TextField className={classes.filed} label="고용일" value={datas.content.hireDate} variant="outlined" disabled/>
          </Paper>          
          <Paper className={classes.paper}>
            <FormControl className={classes.filed} variant="outlined">
                <InputLabel>직책</InputLabel>
                <Select
                  native
                  value={revise.content.title}
                  onChange={handleTitleChange}
                  >
                    <option value="Staff">Staff</option>
                    <option value="Manager">Manager</option>
                    <option value="Assistant Engineer">Assistant Engineer</option>
                    <option value="Engineer">Engineer</option>
                    <option value="Technique Leader">Technique Leader</option>
                    <option value="Senior Staff">Senior Staff</option>
                    <option value="Senior Engineer">Senior Engineer</option>
                  </Select>
              </FormControl>
          </Paper>
        </Grid>
        <Grid item xs={6}>
          <Paper className={classes.paper}>
            <TextField className={classes.filed} label="이름" value={datas.content.firstName + ' ' +datas.content.lastName} variant="outlined" disabled/>
          </Paper>
          <Paper className={classes.paper}>
            <TextField className={classes.filed} label="성별" value={datas.content.gender} variant="outlined" disabled/>
          </Paper>
          <Paper className={classes.paper}>
            <FormControl className={classes.filed} variant="outlined">
              <InputLabel>부서</InputLabel>
              <Select                
                native
                value={revise.content.deptNo}
                onChange={handleDeptChange}
                >
                  <option value="d009">Customer Service</option>
                  <option value="d005">Development</option>
                  <option value="d002">Finance</option>
                  <option value="d003">Human Resources</option>
                  <option value="d001">Marketing</option>
                  <option value="d004">Production</option>
                  <option value="d006">Quality Management</option>
                  <option value="d008">Research</option>
                  <option value="d007">Sales</option>
              </Select>
            </FormControl>
          </Paper>
          <Paper className={classes.paper}>
            <TextField className={classes.filed} label="연봉($)" type="number" defaultValue={datas.content.salary} variant="outlined" onChange={handleSalaryChange}/>
          </Paper>
        </Grid>
      </Grid>
    </Paper>
      <Button className={classes.button} color="secondary" variant="contained" onClick={() => setIsDelete(true) } disabled={isDelete || isPush}>삭제</Button>
      <Button className={classes.button} color="primary" variant="contained" onClick={() => setIsPush(true) } disabled={isDelete || isPush}>제출</Button>
      {isDelete && 
      <Snackbar anchorOrigin={{vertical:'top', horizontal:'center'}} open={isDelete} autoHideDuration={6000} onClose={() => setIsDelete(false)}>
        <Alert onClose={() => setIsDelete(false)} severity="info">
          <Delete empNo={datas.content.empNo} />
        </Alert>
      </Snackbar>
      }
      {isPush && 
      <Snackbar anchorOrigin={{vertical:'top', horizontal:'center'}} open={isPush} autoHideDuration={6000} onClose={() => setIsPush(false)}>
        <Alert onClose={() => setIsPush(false)} severity="info">
          <Push revise={revise} />
        </Alert>
      </Snackbar>     
      }
    </div>
  )
}

export default Editing;