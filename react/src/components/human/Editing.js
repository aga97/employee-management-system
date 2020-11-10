import { Button, Chip, CircularProgress, FormControl, Grid, InputLabel, makeStyles, Paper, Select, TextField } from '@material-ui/core';
import { Restaurant } from '@material-ui/icons';
import axios from 'axios';
import React, { useEffect, useState } from 'react';
import ReviseSalary from './ReviseSalary';

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
  float: 'right',
 }
}));


function Editing(props) {

  const classes = useStyles();

  const [datas, setDatas ] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const [revise, setRevise] = useState({
    content :{
      empNo : '',
      deptNo : '',
      title : '',
      salary : ''
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
    if(e.target.value === datas.content.title) {
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
            salary: e.target.value,
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
            <TextField className={classes.filed} label="직책" defaultValue={datas.content.title} variant="outlined" onChange={handleTitleChange}/>
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
            {//선택으로 할것}
            }
            <TextField className={classes.filed} label="부서" defaultValue={datas.content.deptName} variant="outlined" onChange={handleDeptChange}/>
            <FormControl>
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
            <TextField className={classes.filed} label="연봉($)" defaultValue={datas.content.salary} variant="outlined" onChange={handleSalaryChange}/>
          </Paper>
        </Grid>
      </Grid>
    </Paper>
      <Button className={classes.button} color="secondary" variant="contained">삭제</Button>
      <Button className={classes.button} color="primary" variant="contained">제출</Button>
      {console.log(revise)}
    </div>
  )
}

export default Editing;