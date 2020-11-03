import 'date-fns';
import { Button, Card, CardContent, FormControl, Grid, InputLabel, makeStyles, Select, TextField, Typography } from '@material-ui/core';
import { KeyboardDatePicker, MuiPickersUtilsProvider } from '@material-ui/pickers';
import React, { useState } from 'react';
import DateFnsUtils from '@date-io/date-fns';


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
  }));

function Insert() {

const [birth, setBirth] = useState(new Date());
const [hire, setHire] = useState(new Date());

const [state, setState] = useState({
  gender: 'M',
  department: 'Customer Service',
  title: 'Staff'
})

const handleBirthChange = (date) => {
  setBirth(date);
};
const handleHireChange = (date) => {
  setHire(date);
};
const handleGenderChange = (event) => {
  setState({
    ...state,
    gender: event.target.value,
  })
}
const handleDepartmentChange = (event) => {
  setState({
    ...state,
    department: event.target.value,
  })
}
const handleTitleChange = (event) => {
  setState({
    ...state,
    title: event.target.value,
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
                <TextField required id="firstName" label="Required" />
              </Card>  
            </Grid>  
            <Grid item md>      
              <Card className={classes.control} >
                <Typography>
                  LastName
                </Typography>
                <TextField required id="LastName" label="Required"  />
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
                  value={state.gender}
                  onChange={handleGenderChange}
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
                  format="yyyy/MM/dd"
                  maxDate={new Date()}
                  value={birth}
                  onChange={handleBirthChange}
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
                  value={state.department}
                  onChange={handleDepartmentChange}
                  >
                    <option aria-label="None" value="Customer Service">Customer Service</option>
                    <option value="Development">Development</option>
                    <option value="Finance">Finance</option>
                    <option value="Human Resources">Human Resources</option>
                    <option value="Marketing">Marketing</option>
                    <option value="Production">Production</option>
                    <option value="Quality Management">Quality Management</option>
                    <option value="Research">Research</option>
                    <option value="Sales">Sales</option>
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
                  value={state.title}
                  onChange={handleTitleChange}
                  >
                    <option aria-label="None" value="Staff">Staff</option>
                    <option value="Manager">Manager</option>
                    <option value="Assistant Engineer">Assistant Engineer</option>
                    <option value="Engineer">Engineer</option>
                    <option value="Technique Leader">Technique Leader</option>
                    <option value="Senior Staff">Senior Staff</option>
                    <option value="Senioir Engineer">Senioir Engineer</option>
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
                  format="yyyy/MM/dd"
                  maxDate={new Date()}
                  value={hire}
                  onChange={handleHireChange}
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
              <TextField required id="Salary" label="Required" type="number"/>
            </Card>
          </Grid>         
        </Grid>
        </CardContent>  
        <Button className={classes.button} variant="contained" color="primary" >생성</Button>      
      </Card>
    </div>
  )
}

export default Insert;