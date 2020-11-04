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
import { Container, CircularProgress, Card, Backdrop, CardContent, CardHeader, AppBar, Tabs, Tab, IconButton } from '@material-ui/core';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import * as actions from '../../actions';
import Revise from './Revise';
import ReviseDept from './ReviseDept';
import ReviseTitle from './ReviseTitle';
import ReviseSalary from './ReviseSalary';
import { Close } from '@material-ui/icons';

function TabPanel(props) {
  const { value, index, empNo, children, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === 0 && (
        <Revise empNo={empNo} />
      )}
      {value === 1 && (
        <ReviseDept empNo={empNo} />
      )}
      {value === 2 && (
        <ReviseTitle empNo={empNo} />
      )}
      {value === 3 && (
        <ReviseSalary empNo={empNo} />
      )}
    </div>
  );
}

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
    marginTop: theme.spacing(25), 
  },
  backdrop: {
    zIndex: theme.zIndex.drawer + 1,
    Color: '#fff'
  },
  tap: {
    backgroundColor: theme.palette.background.paper,
  },
}));


export default function Human() {

  const dispatch = useDispatch();

  const { search } = useSelector((state) => ({
    search: state.changeSearch,
  }))

  const [datas, setDatas ] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const [expanded, setExpanded] = useState(false);
  const [revEmp, setRevEmp] = useState(null);

  const [tabIndex, setTabIndex] = useState(0);

  const classes = useStyles();
  
  useEffect(() => {
    let unmounted = false;
    const fetchDatas = async () => {      
      try {           
        setDatas(null);
        setLoading(true);        
        const res = await axios.get('http://localhost:8080/api/employees', search)
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

  const handleChangePage = (event, newPage) => {  
    dispatch(actions.changeSearch({
      ...search.params,
      page : newPage,
    }))
  };

  const handleChangeRowsPerPage = (event) => {
    dispatch(actions.changeSearch({
      ...search.params,
      size: parseInt(event.target.value, 10),
      page:0,
    }))
  };

  const handleTabChange = (event, newValue) => {
    setTabIndex(newValue);
  };

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
    <Container>     
      <TablePagination
      rowsPerPageOptions={[10, 25, 50, 100]}          
      component="div"      
      count={datas.last === false ? -1 : datas.numberOfElements}  
      rowsPerPage={search.params.size}
      page={search.params.page}
      onChangePage={handleChangePage} 
      onChangeRowsPerPage={handleChangeRowsPerPage}         
        />    
    </Container>    
    <TableContainer component={Paper}>
      <Table className={classes.table} aria-label="simple table">
        <TableHead>
          <TableRow>
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
        <TableBody >
          {datas.content.map((text) => (  
            <TableRow hover onClick={() => {
              setExpanded(!expanded)
              setRevEmp(text.empNo)
              }} key={text.empNo} >             
              <TableCell component="th" scope="row" align="center">
                {text.empNo}
              </TableCell>
              <TableCell align="center">{text.firstName} </TableCell>
              <TableCell align="center">{text.lastName}</TableCell>
              <TableCell align="center">{text.gender}</TableCell>
              <TableCell align="center">{text.birthDate}</TableCell>    
              <TableCell align="center">{text.deptName}</TableCell>   
              <TableCell align="center">{text.title}</TableCell>       
              <TableCell align="center">{text.hireDate}</TableCell>               
              <TableCell align="center">${text.salary}</TableCell>     
            </TableRow>   
          ))}
        </TableBody>
      </Table>
    </TableContainer>
    { expanded !== false &&
      <Backdrop className={classes.backdrop} open={expanded} >
      <Card>
        <CardHeader action={
        <IconButton onClick={() => {
          setExpanded(false)
          setTabIndex(0);
          }}><Close/></IconButton>}/>
        <CardContent >
          <AppBar position="static" >
            <Tabs value={tabIndex} onChange={handleTabChange}>
              <Tab label="기본 정보" />
              <Tab label="부서 내역" />
              <Tab label="직책 내역" />
              <Tab label="연봉 내역" />
            </Tabs>
          </AppBar>
          <TabPanel empNo={revEmp} value={tabIndex} index={0}/>
          <TabPanel empNo={revEmp} value={tabIndex} index={1}/>
          <TabPanel empNo={revEmp} value={tabIndex} index={2}/>
          <TabPanel empNo={revEmp} value={tabIndex} index={3}/>
        </CardContent>
      </Card>
    </Backdrop>
    }
   </div>
  );
}