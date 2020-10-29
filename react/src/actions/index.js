export const SUBMIT = 'SUBMIT';
export const INIT = 'INIT';
export const CHANGESEARCH = 'CHANGESEARCH';
export const NAVIGATION = 'NAVIGATION';

export const submit = () => {
    return {
        type: SUBMIT,
    }
}

export const init = () => {
    return {
        type: INIT,
    }
}

export const changeSearch = (params) => {
    return {
        type: CHANGESEARCH,
        params,
        
    }
}

export const navigation = (index) => {
    return {
        type: NAVIGATION,     
        index: index,   
    }
}

// params:{
//     empNo: '',
//     firstName: '',
//     lastName: '',
//     gender: '',
//     birthDate: '',
//     depNo: '',
//     hireDate: '',
//     size: 10,
//     page: 0,
//     sort: 'empNo',
//   }, 