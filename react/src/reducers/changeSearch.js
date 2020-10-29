import {CHANGESEARCH, INIT} from '../actions'

const initState = {
    params:{
        empNo: '',
        firstName: '',
        lastName: '',
        gender: '',
        birthDate: '',
        deptNo: '',
        hireDate: '',
        size: 10,
        page: 0,
        sort: 'empNo',
      }, 
};

const changeSearch = (state = initState, action) => {
    switch(action.type) {
        case CHANGESEARCH:
            return {
                ...state,
               params: {
                empNO: action.params.empNO,
                firstName: action.params.firstName,
                lastName: action.params.lastName,
                gender: action.params.gender,
                birthDate: action.params.birthDate,
                deptNo: action.params.deptNo,
                hireDate: action.params.hireDate,
                size: action.params.size,
                page: action.params.page,
                sort: action.params.sort,
               }
            }
        case INIT:
            return{
                ...state,
                params: initState.params
            }
        default:
            return state;
    }
}

export default changeSearch;