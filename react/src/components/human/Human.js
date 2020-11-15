import React, { useEffect, useMemo, useReducer, useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import Revise from './Revise';
import ReviseDept from './ReviseDept';
import ReviseTitle from './ReviseTitle';
import ReviseSalary from './ReviseSalary';
import { Close } from '@material-ui/icons';
import {Loading} from "./theme/loading";
import { Grid, Table, TableFilterRow, TableHeaderRow, VirtualTable } from '@devexpress/dx-react-grid-material-ui';
import { createRowCache, DataTypeProvider, FilteringState, SortingState, VirtualTableState } from '@devexpress/dx-react-grid';
import { AppBar, Backdrop, Card, CardContent, CardHeader, IconButton, Tab, Tabs } from '@material-ui/core';
import Editing from './Editing';


const VIRTUAL_PAGE_SIZE = 100;
const MAX_ROWS = 50000;
const URL = "http://localhost:3000/api/employees";
const getRowId = row => row.empNo;

const initialState = {
  rows: [],
  skip: 0,
  requestedSkip: 0,
  size: VIRTUAL_PAGE_SIZE * 2,
  totalCount: 0,
  loading: false,
  lastQuery: '',
  sorting: [],
  filters: [],
  forceReload: false,
};

function reducer(state, { type, payload }) {
  switch (type) {
    case 'UPDATE_ROWS':
      return {
        ...state,
        ...payload,
        loading: false,
      };
    case 'CHANGE_SORTING':
      return {
        ...state,
        forceReload: true,
        rows: [],
        sorting: payload,
      };
    case 'CHANGE_FILTERS':
      return {
        ...state,
        forceReload: true,
        requestedSkip: 0,
        rows: [],
        filters: payload,
      };
    case 'START_LOADING':
      return {
        ...state,
        requestedSkip: payload.requestedSkip ,
        size: payload.size,
      };
    case 'REQUEST_ERROR':
      return {
        ...state,
        loading: false,
      };
    case 'FETCH_INIT':
      return {
        ...state,
        loading: true,
        forceReload: false,
      };
    case 'UPDATE_QUERY':
      return {
        ...state,
        lastQuery: payload,
      };
    default:
      return state;
  }
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
      {value === 4 && (
        <Editing empNo={empNo} />
      )}
    </div>
  );
}

const CurrencyFormatter = ({ value }) => (
  <b>
    {value.toLocaleString('en-US', { style: 'currency', currency: 'USD' })}
  </b>
);
const CurrencyTypeProvider = props => (
  <DataTypeProvider
    formatterComponent={CurrencyFormatter}
    {...props}
  />
);

export default function Human() {
  const [state, dispatch] = useReducer(reducer, initialState);
  const [columns] = useState([
    {name: "empNo", title: "사원 번호", getCellValue: row => row.empNo},
    {name: "firstName", title: "First Name", getCellValue: row => row.firstName},
    {name: "lastName", title: "Last Name", getCellValue: row => row.lastName},
    {name: "gender", title: "성별", getCellValue: row => row.gender},
    {name: "birthDate", title: "생년월일", getCellValue: row => row.birthDate},
    {name: "deptName", title: "부서", getCellValue: row => row.deptName},
    {name: "title", title: "직책", getCellValue: row => row.title},
    {name: "salary", title: "연봉", getCellValue: row => row.salary},
  ]);
  const [currencyColumn] = useState(['salary']);

  const cache = useMemo(() => createRowCache(VIRTUAL_PAGE_SIZE), []);


  const [sortingStateColumnExtensions] = useState([
      {columnName: 'gender', sortingEnabled: false},
      {columnName: 'title', sortingEnabled: false},
      {columnName: 'salary', sortingEnabled: false},
  ]);
  const [filteringStateColumnExtensions] = useState([
      { columnName: 'title', filteringEnabled: false },
      { columnName: 'salary', filteringEnabled: false },
  ]);

  const [expanded, setExpanded] = useState(false);
  const [revEmp, setRevEmp] = useState(null);
  const [tabIndex, setTabIndex] = useState(0);

  const classes = useStyles();

  const updateRows = (skip, count, newTotalCount) => {
    dispatch({
      type: 'UPDATE_ROWS',
      payload: {
        skip: Math.min(skip, newTotalCount),
        rows: cache.getRows(skip, count),
        totalCount: newTotalCount < MAX_ROWS ? newTotalCount : MAX_ROWS,
      },
    });
  };
  const getRemoteRows = (requestedSkip, size) => {
    dispatch({ type: 'START_LOADING', payload: { requestedSkip, size } });
  };

  const buildQueryString = () => {
    const {
      requestedSkip, size, filters, sorting,
    } = state;
    const filterStr = filters
      .map(({ columnName, value, operation }) => (
        `${columnName}=${value}`
      )).join('&');
    const sortingConfig = sorting
      .map(({ columnName, direction }) => ({
        selector: columnName,
        desc: direction === 'desc',
      }));
    const filterQuery = filterStr ? `&${filterStr}` : '';
    const sortSelect= sortingConfig ? `&sort=${sortingConfig[0] ? sortingConfig[0].selector : ''}` : '';
    let sortQuery = '', sortDirection = '';
    if(sortingConfig[0]) {
      sortDirection = ',' + (sortingConfig[0].desc ? 'desc' : '');
    }
    sortQuery = sortSelect + sortDirection;
    return `${URL}?page=${ requestedSkip / size }&size=${size}${filterQuery}${sortQuery}`;
  };

  const loadData = () => {
    const {
      requestedSkip, size, lastQuery, loading, forceReload,
    } = state;
    const query = buildQueryString();
    if ((query !== lastQuery || forceReload) && !loading) {
      if (forceReload) {
        cache.invalidate();
      }
      const cached = cache.getRows(requestedSkip, size);
      if (cached.length === size) {
        updateRows(requestedSkip, size, MAX_ROWS);
      } else {
        dispatch({ type: 'FETCH_INIT' });
        fetch(query)
          .then(response => response.json())
          .then(({ content }) => {
            cache.setRows(requestedSkip, content);
            updateRows(requestedSkip, size, MAX_ROWS);
          })
          .catch(() => dispatch({ type: 'REQUEST_ERROR' }));
      }
      dispatch({ type: 'UPDATE_QUERY', payload: query });
    }
};

  const changeFilters = (value) => {
    dispatch({ type: 'CHANGE_FILTERS', payload: value });
  };

  const changeSorting = (value) => {
    dispatch({ type: 'CHANGE_SORTING', payload: value });
  };

  
  const tableStyles = {
    true: {
      color: 'white',
      backgroundColor : '#F5A9A9',
    }
  }

  const clickBackdrop = ({ row,value, style, ...restProps }) => (
    <Table.Row
      hover
      {...restProps}
      // eslint-disable-next-line no-alert
      // onClick={() => alert(JSON.stringify(row))}
      onClick={() => {
        setExpanded(!expanded);
        setRevEmp(row.empNo);
      }}
      style={{
        cursor: 'pointer',
        ...tableStyles[row.retirement],
      }}
    />  
  );

  // const whiteTextCell = ({ value, row, ...restProps }) => (
  //   <Table.Cell>
  //     <span
  //       style={{
  //         ...tableStyles[row.retirement],
  //       }}
  //     >
  //       {value}
  //     </span>
  //   </Table.Cell>
  // );


  const handleTabChange = (event, newValue) => {
    setTabIndex(newValue);
  };

  useEffect(() => loadData());

  const {
    rows, skip, totalCount, loading, sorting, filters,
  } = state;

  return (    
    <Paper  style={{width:'99.8%'}}/* 사이즈 자동조절이 잘안되서 width 지정 */>
      <Grid rows={rows} columns={columns} getRowId={getRowId} > 
          <CurrencyTypeProvider for={currencyColumn} />
          <VirtualTableState 
          infiniteScrolling         
          loading={loading}
          totalRowCount={totalCount}
          pageSize={VIRTUAL_PAGE_SIZE}
          skip={skip}
          getRows={getRemoteRows}
          />
          <SortingState
            sorting={sorting}
            onSortingChange={changeSorting}
            columnExtensions={sortingStateColumnExtensions}
          />
          <FilteringState
            filters={filters}
            onFiltersChange={changeFilters}
            columnExtensions={filteringStateColumnExtensions}
          />
          <VirtualTable rowComponent={clickBackdrop} />
          <TableHeaderRow showSortingControls/>
          <TableFilterRow />
         
      </Grid>
    {loading && <Loading/>}
   
    {expanded !== false &&
      <Backdrop className={classes.backdrop} open={expanded} >
      <Card >
        <CardHeader action={
        <IconButton onClick={() => {
          setExpanded(false)
          setTabIndex(0);
          }}><Close/></IconButton>}/>
        <CardContent>
          <AppBar position="static">
            <Tabs value={tabIndex} onChange={handleTabChange}>
              <Tab label="기본 정보" />
              <Tab label="부서 내역" />
              <Tab label="직책 내역" />
              <Tab label="연봉 내역" />
              <Tab label="수정" />
            </Tabs>
          </AppBar>
          <TabPanel empNo={revEmp} value={tabIndex} index={tabIndex}/>
        </CardContent>
      </Card>
    </Backdrop>
    }
     </Paper>

  );
}