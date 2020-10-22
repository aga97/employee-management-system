import React, { useEffect, useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TablePagination from '@material-ui/core/TablePagination';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import TextField from '@material-ui/core/TextField';
import { Button, Container, CircularProgress } from '@material-ui/core';
import axios from 'axios';

const useStyles = makeStyles((theme) => ({
  table: {
    minWidth: 650,
  },
  text: {
    margin: theme.spacing(1),
    width: '25ch',
  },
  search: {
    '& > *': {
      margin: theme.spacing(2),  
      height: '6ch',      
      padding: '1px',   
    },
  },
  tabletext: {
    fontWeight: 'bold',
  },
  progress: {
    width: '100%',  
    '& > * + *': {
      marginTop: theme.spacing(10),
    },
    marginTop: theme.spacing(30),
  }
}));

const empDatas = {
  params:{
    empNo: '',
    firstName: '',
    lastName: '',
    gender: '',
    birthDate: '',
    depNo: '',
    hireDate: '',
    size: 10,
    page: 0,
    sort: 'empNo',
  }, 
}


export default function Human() {

  const [search, setSearch] = useState(empDatas);
  

  const [datas, setDatas ] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const [first, setFirst] = useState(null);
  const classes = useStyles();

  
  useEffect(() => {
    let unmounted = false;
    const fetchDatas = async () => {      
      try {           
        setDatas(null);
        setLoading(true);        
        const res = await axios.get('http://localhost:8080/employees', search)
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
    
  },[search])

  const onChange = (e) => {
    setFirst(e.target.value);
  };

  const onClick = (e) => {      
    setSearch((prevState) => ({      
      params:{
        ...prevState.params,
        firstName:first
      }
    }))
    setFirst('');
  }

  const handleChangePage = (event, newPage) => {
    setSearch((prevState) => ({      
      params:{
        ...prevState.params,
        page:newPage}
    }));
  };

  const handleChangeRowsPerPage = (event) => {
    setSearch((prevState) => ({
      
      params:{
        ...prevState.params,
        size:parseInt(event.target.value,10),
        page:0}
    }));
  };

  if(loading) {
      return(
      <div className={classes.progress}>
        <CircularProgress color="secondary" size={100} />        
      </div>
    )
  }
  if(error) {
    return <div>error</div>
  }
  if (!datas) return null;

  return (
    <div>
    <TableContainer component={Paper}>
      <Table className={classes.table} aria-label="simple table">
        <TableHead>
          <TableRow >
            <TableCell className={classes.tabletext} align="center">직원 번호</TableCell>
            <TableCell className={classes.tabletext} align="center">FirstName</TableCell>
            <TableCell className={classes.tabletext} align="center">LastName</TableCell>
            <TableCell className={classes.tabletext} align="center">성별</TableCell>
            <TableCell className={classes.tabletext} align="center">생년월일</TableCell>
            <TableCell className={classes.tabletext} align="center">부서</TableCell>
            <TableCell className={classes.tabletext} align="center">직책</TableCell>
            <TableCell className={classes.tabletext} align="center">고용일</TableCell>
            <TableCell className={classes.tabletext} align="center">연봉</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {datas.content.map((text) => (
            <TableRow key={text.empNo}>
              <TableCell component="th" scope="row" align="center">
                {text.empNo}
              </TableCell>
              <TableCell align="center">{text.firstName}</TableCell>
              <TableCell align="center">{text.lastName}</TableCell>
              <TableCell align="center">{text.gender}</TableCell>
              <TableCell align="center">{text.birthDate}</TableCell>    
              <TableCell align="center">{text.deptName}</TableCell>   
              <TableCell align="center">{text.title}</TableCell>       
              <TableCell align="center">{text.hireDate}</TableCell>               
              <TableCell align="center">{text.salary}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
    <TablePagination
      rowsPerPageOptions={[10, 25, 50, 100]}          
      component="div"      
      count={datas.totalPages}   
      rowsPerPage={search.params.size}
      page={search.params.page}
      onChangePage={handleChangePage} 
      onChangeRowsPerPage={handleChangeRowsPerPage}         
        />
    <Container>
      <div className={classes.search} noValidate autoComplete='off'>        
      <TextField id="outlined-search" label="Search field" type="search" variant="filled" size="small" defaultValue={search.params.firstName}
      onChange={onChange} onKeyPress={e=>{
        if(e.key==='Enter'){
          onClick();
        }
      }}/>
      <Button onClick={onClick} variant="contained" size="large" >Search</Button>     
      </div>
    </Container>    
   </div>
  );
}