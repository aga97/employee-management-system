import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import TextField from '@material-ui/core/TextField';
import { Button, Container } from '@material-ui/core';

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
  }
}));

function createData(department, firstName, lastName, salary, date) {
  return { department, firstName, lastName, salary, date };
}

const rows = [
  createData('Human Resource', 'Kevin', 'Washinton', 2400, 6),
  createData('Human Resource', 'jung', 'shin', 3000, 8),
  createData('Data', 'asd', 'ergas', 2000, 2),
  createData('Finance', 'qwe', 'asdzxc', 2200, 3),
  createData('Plan', 'gdfg', 'asfasf', 3000, 6),
];



export default function Human() {
  const classes = useStyles();

  return (
    <div>
    <TableContainer component={Paper}>
      <Table className={classes.table} aria-label="simple table">
        <TableHead>
          <TableRow >
            <TableCell className={classes.tabletext}>Department</TableCell>
            <TableCell className={classes.tabletext} align="right">FirstName</TableCell>
            <TableCell className={classes.tabletext} align="right">LastName</TableCell>
            <TableCell className={classes.tabletext} align="right">Salary</TableCell>
            <TableCell className={classes.tabletext} align="right">Date(Year)</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <TableRow key={row.department}>
              <TableCell component="th" scope="row">
                {row.department}
              </TableCell>
              <TableCell align="right">{row.firstName}</TableCell>
              <TableCell align="right">{row.lastName}</TableCell>
              <TableCell align="right">{row.salary}</TableCell>
              <TableCell align="right">{row.date}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
     
    </TableContainer>
    <Container>
      <form className={classes.search} noValidate autoComplete='off'>        
      <TextField id="outlined-search" label="Search field" type="search" variant="filled" size="small" />
      <Button variant="contained" size="large" >Search</Button>     
      </form>
    </Container>
   </div>
  );
}