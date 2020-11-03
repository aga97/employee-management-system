import { Card, CircularProgress, Grid, makeStyles, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography } from '@material-ui/core';
import axios from 'axios';
import React, { useEffect, useState } from 'react';

const useStyles = makeStyles((theme) => ({
    root: {
        margin: theme.spacing(2),
        padding: '1px',
    },
    text: {
        fontWeight: 'bold',
    },
    table: {
        backgroundColor: theme.palette.action.hover,
    }

}));

export default function DepInside(props) {

    const classes = useStyles();
    const dep = props.deptNo;

    //Data, Loading, Error
    const [datas, setDatas] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {

        let unmounted = false;

        const fetchDatas = async () => {
            try {
                setDatas(null);
                setLoading(true);
                const res = await axios.get('http://localhost:3000/api/departments/' + dep);
                if (!unmounted)
                    setDatas(res.data);
            } catch (e) {
                setError(e);
            }
            if (!unmounted) setLoading(false);
        };
        fetchDatas();
        return () => {//clean up      
            unmounted = true;
        }
    }, [dep])

    if (loading) {
        return (
            <div >
                <CircularProgress color="secondary" size={30} />
            </div>
        )
    }
    if (error) {
        return <div>error</div>
    }
    if (!datas) return null;

    return (
        <Grid container spacing={3} direction="row" justify="center" alignItems="stretch">
            <Grid item xs={6} sm={3} md={12}  >
                <Card>
                    <Typography className={classes.text}>
                        부서 총 인원 : {datas.size} 명 | 남 : {datas.genderInfo[0].size}   여 : {datas.genderInfo[1].size}
                    </Typography>
                    <Typography className={classes.text}>
                        부서장 : {datas.deptManager} (부임일 : {datas.fromDate})
                    </Typography>
                </Card>
            </Grid>
            <Grid item xs={6} sm={3} md={12}  >
                <Card>
                    <Typography className={classes.text}>
                        직책별 연봉
                    </Typography>
                    <TableContainer >
                        <Table aria-label="depart" >
                            <TableHead>
                                <TableRow>
                                    <TableCell align="center">직책</TableCell>
                                    <TableCell align="center">평균 연봉</TableCell>
                                    <TableCell align="center">최저 연봉</TableCell>
                                    <TableCell align="center">최고 연봉</TableCell>
                                    <TableCell align="center">인원</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {datas.salaryInfo.map((salary) => (
                                    <TableRow key={salary.title}>
                                        <TableCell align="center">{salary.title}</TableCell>
                                        <TableCell align="center">{salary.avgSalary}</TableCell>
                                        <TableCell align="center">{salary.minSalary}</TableCell>
                                        <TableCell align="center">{salary.maxSalary}</TableCell>
                                        <TableCell align="center">{salary.size}</TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                </Card>
            </Grid>
            <Grid item xs={6} sm={3} md={12}  >
                <Card>
                    <TableContainer>
                        <Typography className={classes.text}>
                            역대 부서장
                        </Typography>
                        <Table aria-label="manager">
                            <TableHead>
                                <TableRow>
                                    <TableCell align="center">이름</TableCell>
                                    <TableCell align="center">재임 기간</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {datas.deptManagerHistory.map((manager) => (
                                    <TableRow key={manager.deptManager}>
                                        <TableCell align="center">{manager.deptManager}</TableCell>
                                        <TableCell align="center">{manager.fromDate} ~ {manager.toDate}</TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                </Card>
            </Grid>
        </Grid>

    );
}
