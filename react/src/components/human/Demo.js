import React, {useEffect, useState} from "react";
import Paper from "@material-ui/core/Paper";
import {Grid, Table, TableFilterRow, TableHeaderRow, VirtualTable,} from "@devexpress/dx-react-grid-material-ui";
import {FilteringState, SortingState} from "@devexpress/dx-react-grid";
import {Loading} from "./theme/loading";

const URL = "http://localhost:3000/api/employees";

export default () => {
    const [columns] = useState([
        {name: "empNo", title: "사원 번호"},
        {name: "firstName", title: "First Name"},
        {name: "lastName", title: "Last Name"},
        {name: "gender", title: "성별"},
        {name: "birthDate", title: "생년월일"},
        {name: "deptName", title: "부서"},
        {name: "title", title: "직책"},
        {name: "salary", title: "연봉"}
    ]);
    const [rows, setRows] = useState([]);
    const [loading, setLoading] = useState(false);
    const [lastQuery, setLastQuery] = useState();
    const [sorting, setSorting] = useState([{columnName: 'empNo', direction: 'asc'}]);
    const [sortingStateColumnExtensions] = useState([
        {columnName: 'title', sortingEnabled: false},
        {columnName: 'salary', sortingEnabled: false},
    ]);
    const [filters, setFilters] = useState([]);
    const [filteringStateColumnExtensions] = useState([
        { columnName: 'title', filteringEnabled: false },
        { columnName: 'salary', filteringEnabled: false },
    ]);

    const getQueryString = () => {
        let queryString = `${URL}`;

        if (sorting.length) {
            const sort = sorting[0];
            queryString = `${queryString}?sort=${sort.columnName},${sort.direction}`;
        }

        if (filters.length) {
            let filterString = "";
            filters.forEach(filter => {
                filterString = filterString.concat(`&${filter.columnName}=${filter.value}`);
            });
            queryString = `${queryString}${filterString}`
            console.log(queryString);
        }
        return queryString;
    }

    const loadData = () => {
        const queryString = getQueryString();

        if (queryString !== lastQuery && !loading) {
            setLoading(true);
            fetch(queryString)
                .then((response) => response.json())
                .then(({content}) => {
                    setRows(content);
                    setLoading(false);
                })
                .catch(() => setLoading(false));
            setLastQuery(queryString);
        }
    };

    useEffect(() => loadData());

    return (
        <Paper style={{width: '99.8%'}} /* 사이즈 자동조절이 잘안되서 width 지정 */>
            <Grid rows={rows} columns={columns}>
                <SortingState
                    columnExtensions={sortingStateColumnExtensions}
                    sorting={sorting}
                    onSortingChange={setSorting}
                />
                <FilteringState
                    onFiltersChange={setFilters}
                    columnExtensions={filteringStateColumnExtensions}
                />
                <VirtualTable/>
                <TableHeaderRow showSortingControls/>
                <TableFilterRow/>
            </Grid>
            {loading && <Loading/>}
        </Paper>
    );
};
