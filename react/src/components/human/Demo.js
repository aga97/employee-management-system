import React, {useEffect, useState} from "react";
import Paper from "@material-ui/core/Paper";
import {Grid, Table, TableHeaderRow,} from "@devexpress/dx-react-grid-material-ui";

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

    const loadData = () => {
        const queryString = `${URL}`;

        if (queryString !== lastQuery && !loading) {
            setLoading(true);
            fetch(queryString)
                .then((response) => response.json())
                .then(({content}) => {
                    setRows(content);
                })
                .catch(() => setLoading(false));
            setLastQuery(queryString);
        }
    };

    useEffect(() => loadData());

    return (
        <Paper>
            <Grid rows={rows} columns={columns}>
                <Table/>
                <TableHeaderRow/>
            </Grid>
        </Paper>
    );
};
