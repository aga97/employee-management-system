import { CircularProgress, makeStyles, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from '@material-ui/core';
import axios from 'axios';
import React, { useEffect, useState } from 'react';

const useStyles = makeStyles((theme) => ({    
    container: {
        maxHeight: 440,
    },
   }));

function ReviseTitle(props) {

  const [datas, setDatas ] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const classes = useStyles();
  
  useEffect(() => {
    let unmounted = false;
    const fetchDatas = async () => {      
      try {           
        setDatas(null);
        setLoading(true);        
        const res = await axios.get('http://localhost:8080/api/employee/' + props.empNo + '/title')
        if(!unmounted)
          setDatas(res.data);     
      } catch (e) {        
        setError(e);
      }
      if(!unmounted) setLoading(false);            
    };
    fetchDatas();  
    return () => {//clean up      
      unmounted = true;
    }  
  },[props.empNo])
  if(loading) {
    return(
    <div >
      <CircularProgress color="secondary" />        
    </div>
    )
  }
  if(error) {
    return <div>error</div>
  }
  if (!datas) return null;

  return (
    <div>
        <Paper>
        <TableContainer className={classes.container}>  
        <Table stickyHeader>
            <TableHead>
              <TableRow>
                <TableCell align="center">직책</TableCell>
                <TableCell align="center">시작일</TableCell>
                <TableCell align="center">종료일</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
                {datas.content.map((text) => (
                    <TableRow key={text.title}>
                    <TableCell align="center">{text.title}</TableCell>
                    <TableCell align="center">{text.fromDate}</TableCell>
                    <TableCell align="center">{text.toDate === '9999-01-01' ? '~' : text.toDate}</TableCell>
                    </TableRow>
                ))}
            </TableBody>
        </Table>
        </TableContainer>   
        </Paper>
    </div>
  )
}

export default ReviseTitle;