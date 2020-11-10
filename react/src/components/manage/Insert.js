import 'date-fns';
import { AppBar, Backdrop, Button, Card, CardContent, CardHeader, FormControl, Grid, IconButton, InputLabel, makeStyles, Select, TextField, Typography } from '@material-ui/core';
import { KeyboardDatePicker, MuiPickersUtilsProvider } from '@material-ui/pickers';
import React, { useState } from 'react';
import DateFnsUtils from '@date-io/date-fns';
import { Close } from '@material-ui/icons';
import InsertBack from './InsertBack';


const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  paper: {
    height: 140,
    width: 100,
  },
  control: {
    padding: theme.spacing(2),
    height: 140,
  },
  button: {
    margin: theme.spacing(2),
  }, 
  backdrop: {
    zIndex: theme.zIndex.drawer + 1,
    Color: '#fff'
  },
  }));

function dateFormatting(date) {
  let Fdate = date;
  if(Fdate < 10) Fdate = '0' + Fdate;
  return Fdate;
}

function Insert() {

const [birthDate, setBirthDate] = useState(new Date());
const [hireDate, setHireDate] = useState(new Date());
const [firstName, setFirstName] = useState('');
const [lastName, setLastName] = useState('');
const [gender, setGender] = useState('M');
const [deptNo, setDeptNo] = useState('d009');
const [title, setTitle] = useState('Staff');
const [salary, setSalary] = useState(0);
const [firstValid, setFirstValid] = useState(false);
const [lastValid, setLastValid] = useState(false);
const [salaryValid, setSalaryValid] = useState(false);

const [expanded, setExpanded] = useState(false);

const [state, setState] = useState({
  gender: 'M',
  deptNo: 'd009',
  title: 'Staff',
  firstName: '',
  lastName: '',
  salary: 0 ,
  birthDate: '',
  hireDate: '',
})

const handleInput = (event) => { 

  const birth = birthDate.getFullYear() + '-' + dateFormatting((birthDate.getMonth() + 1)) + '-' + dateFormatting(birthDate.getDate());
  const hire = hireDate.getFullYear() + '-' + dateFormatting((hireDate.getMonth() + 1))  + '-' + dateFormatting(hireDate.getDate());
  setState({
    ...state,
    gender: gender,
    deptNo: deptNo,
    title: title,
    firstName: firstName,
    lastName: lastName,
    salary: salary,
    birthDate: birth,
    hireDate: hire,
  })
}

const classes = useStyles();

  return (
   
    <div >
      <Card>
        <CardContent>
          <Typography variant="h4">
            Create New Employee
          </Typography>
        </CardContent>
      </Card>
      
      <Card >
        <CardContent>
        <Grid container className={classes.root} spacing={2} direction="row" justify="center" alignItems="center">
            <Grid item md>
              <Card className={classes.control} >
                <Typography>
                  FirstName
                </Typography>
                <TextField required id="firstName" label="Required" onChange={(e) => {
                  setFirstName(e.target.value);
                  if(e.target.value.length > 0) setFirstValid(true);
                  else setFirstValid(false);
                  }} />
              </Card>  
            </Grid>  
            <Grid item md>      
              <Card className={classes.control} >
                <Typography>
                  LastName
                </Typography>
                <TextField required id="LastName" label="Required" onChange={(e) => {
                  setLastName(e.target.value)
                  if(e.target.value.length > 0) setLastValid(true);
                  else setLastValid(false);
                  }} />
              </Card>
            </Grid>
          <Grid item md>
            <Card className={classes.control} >
              <Typography>
                Gender
              </Typography>
              <FormControl>
                <InputLabel htmlFor="gender-select"/>
                <Select
                  native
                  value={gender}
                  onChange={(e) => setGender(e.target.value)}
                  >
                    <option aria-label="None" value="M">Male</option>
                    <option value="F">Female</option>
                  </Select>
              </FormControl>
            </Card>
          </Grid>
          <Grid item md>
            <Card className={classes.control} >
              <Typography>
                BirthDate
              </Typography>
              <MuiPickersUtilsProvider utils={DateFnsUtils}>
                <KeyboardDatePicker 
                  required                  
                  margin="normal"
                  id="birth-picker"
                  label="Date"
                  format="yyyy-MM-dd"
                  maxDate={new Date()}
                  value={birthDate}
                  onChange={(date) => setBirthDate(date)}
                  KeyboardButtonProps={{
                    'aria-label' : 'change date',
                  }}
                />
              </MuiPickersUtilsProvider>
            </Card>
          </Grid>
        </Grid>
        <Grid container className={classes.root} spacing={2} direction="row" justify="center" alignItems="center">
          <Grid item md>
            <Card className={classes.control} >
              <Typography>
                Department
              </Typography>
              <FormControl>
                <InputLabel htmlFor="department-select"/>
                <Select
                  native
                  value={deptNo}
                  onChange={(e) => setDeptNo(e.target.value)}
                  >
                    <option aria-label="None" value="d009">Customer Service</option>
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
            </Card>
          </Grid>
          <Grid item md>
            <Card className={classes.control} >
              <Typography>
                Title
              </Typography>
              <FormControl>
                <InputLabel htmlFor="title-select"/>
                <Select
                  native
                  value={title}
                  onChange={(e) => setTitle(e.target.value)}
                  >
                    <option aria-label="None" value="Staff">Staff</option>
                    <option value="Manager">Manager</option>
                    <option value="Assistant Engineer">Assistant Engineer</option>
                    <option value="Engineer">Engineer</option>
                    <option value="Technique Leader">Technique Leader</option>
                    <option value="Senior Staff">Senior Staff</option>
                    <option value="Senior Engineer">Senior Engineer</option>
                  </Select>
              </FormControl>
            </Card>
          </Grid>
          <Grid item md >
            <Card className={classes.control} >
              <Typography>
                HireDate
              </Typography>
              <MuiPickersUtilsProvider utils={DateFnsUtils}>
                <KeyboardDatePicker 
                  required
                  margin="normal"
                  id="hire-picker"
                  label="Date"
                  format="yyyy-MM-dd"
                  maxDate={new Date()}
                  value={hireDate}
                  onChange={(date) => setHireDate(date)}
                  KeyboardButtonProps={{
                    'aria-label' : 'change date',
                  }}
                />
              </MuiPickersUtilsProvider>
            </Card>
          </Grid>
          <Grid item md>
            <Card className={classes.control} >
              <Typography>
                Salary
              </Typography>
              <TextField required id="salary" label="Required" type="number" onChange={(e) => {
                setSalary(e.target.value)
                if(e.target.value.length > 0) setSalaryValid(true);
                else setSalaryValid(false);
                }}/>
            </Card>
          </Grid>         
        </Grid>
        </CardContent>  
        <Button className={classes.button} variant="contained" color="primary" disabled={!(firstValid && lastValid && salaryValid)}
        onClick={() => {
          handleInput();
          setExpanded(!expanded);
          }} >생성</Button>     
      { expanded === true &&
      <Backdrop className={classes.backdrop} open={expanded} >
        <Card>
        <CardHeader action={
        <IconButton onClick={() => {
          setExpanded(false)
          }}><Close/></IconButton>}/>
        <CardContent >
          <AppBar position="static" />
          <InsertBack content={state}/>
          {console.log(state)}
        </CardContent>
          
        </Card>
      </Backdrop>
      } 
      </Card>
    </div>
  )
}

export default Insert;