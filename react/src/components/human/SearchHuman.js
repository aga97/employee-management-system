import { debounce, FormControl, InputLabel, makeStyles, Select, TextField } from '@material-ui/core';
import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Route } from 'react-router-dom';
import Human from './Human';
import * as actions from '../../actions';

const useStyles = makeStyles((theme) => ({   
    search: {
      '& > *': {
        margin: theme.spacing(2),  
        height: '6ch',      
        padding: '1px',   
      },
    },
  }));

function Home(props){

    const dispatch = useDispatch();
    const { search } = useSelector((state) => ({
      search: state.changeSearch,
    }))
    const classes = useStyles();

    const onChangeDebounced = debounce((value) =>{
        dispatch(actions.changeSearch({
            ...search.params,
            firstName: value
        }));
    },500 )
    const onChange = (e) => {
        onChangeDebounced(e.target.value);
      };

    const onChangeDebouncedLast = debounce((value) =>{
        dispatch(actions.changeSearch({
            ...search.params,
            lastName: value
        }));
    },500 )
    const onChangeLast = (e) => {
        onChangeDebouncedLast(e.target.value);
      };
    const onChangeGender = (e) => {
        dispatch(actions.changeSearch({
            ...search.params,
            gender:e.target.value,
        }))
    }
    const onChangeDep = (e) => {
        dispatch(actions.changeSearch({
            ...search.params,
            deptNo:e.target.value,
        }))
    }
    const onChangeSort = (e) => {
        dispatch(actions.changeSearch({
            ...search.params,
            sort:e.target.value,
        }))
    }

    useEffect(() => {                       
        dispatch(actions.navigation(1));
        //오류 발생하는데 먼지 모르겟슴
        return () => {//clean up    
            dispatch(actions.init());          
        }
        
      },[dispatch])
    


    return (      
        <div className={classes.search} noValidate autoComplete='off'>      
            <TextField id="outlined-search" label="FirstName" type="search" variant="filled" size="small" onChange={onChange} defaultValue={search.params.firstName}/>    
            <TextField id="outlined-search" label="LastName" type="search" variant="filled" size="small" onChange={onChangeLast} defaultValue={search.params.lastName}/>   
            <FormControl variant="filled">
                <InputLabel htmlFor="gender-selection">Gender</InputLabel>
                <Select
                native
                value={search.params.gender}
                onChange={onChangeGender}               
                >
                <option aria-label="None" value={''} />
                <option value={'M'}>Male</option>
                <option value={'F'}>Female</option>
                </Select>
            </FormControl>
            <FormControl variant="filled">
                <InputLabel htmlFor="department-selection">Department</InputLabel>
                <Select
                native
                value={search.params.deptNo}
                onChange={onChangeDep}               
                >
                <option aria-label="None" value={''} />
                <option value={'d001'}>Marketing</option>
                <option value={'d002'}>Finance</option>
                <option value={'d003'}>Human Resources</option>
                <option value={'d004'}>Production</option>
                <option value={'d005'}>Development</option>
                <option value={'d006'}>Quality Management</option>
                <option value={'d007'}>Sales</option>
                <option value={'d008'}>Research</option>
                <option value={'d009'}>Customer Service</option>
                </Select>
            </FormControl>
            <FormControl variant="filled">
                <InputLabel htmlFor="department-selection">Sort</InputLabel>
                <Select
                native
                value={search.params.sort}
                onChange={onChangeSort}               
                >
                <option aria-label="first" value={'empNo'}>직원 번호</option>
                <option value={'empNo,desc'}>직원 번호(내림차순)</option>
                <option value={'deptName'}>부서 이름</option>
                <option value={'deptName,desc'}>부서 이름(내림차순)</option>
                <option value={'birthDate'}>생년월일</option>
                <option value={'birthDate,desc'}>생년월일(내림차순)</option>
                <option value={'firstName'}>FirstName</option>
                <option value={'firstName,desc'}>FirstName(내림차순)</option>
                <option value={'lastName'}>LastName </option>
                <option value={'lastName,desc'}>LastName(내림차순)</option>
                <option value={'hireDate'}>고용일</option>
                <option value={'hireDate,desc'}>고용일(내림차순)</option>
                </Select>
            </FormControl>
            <Route component={Human}/> 
        </div>
     
    );
}
export default Home;