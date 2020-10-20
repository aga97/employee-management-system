import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,   
  },
  paper: {
    padding: theme.spacing(2),
    textAlign: 'center',
    color: theme.palette.text.primary,   
  },
}));

export default function Department() {
  const classes = useStyles();

  return (
    <div className={classes.root}>
      <Grid container spacing={3}>
        <Grid item xs>
          <Paper className={classes.paper}>Finance</Paper>
        </Grid>
        <Grid item xs>
          <Paper className={classes.paper}>Plan</Paper>
        </Grid>
      </Grid>
      <Grid container spacing={3}>
        <Grid item xs>
          <Paper className={classes.paper}>Computer</Paper>
        </Grid>
        <Grid item xs>
          <Paper className={classes.paper}>머시기 등등 ..</Paper>
        </Grid>
      </Grid>
      <Grid container spacing={3}>
        <Grid item xs>
          <Paper className={classes.paper}>5</Paper>
        </Grid>
        <Grid item xs>
          <Paper className={classes.paper}>6</Paper>
        </Grid>
      </Grid>
      <Grid container spacing={3}>
        <Grid item xs>
          <Paper className={classes.paper}>7</Paper>
        </Grid>
        <Grid item xs>
          <Paper className={classes.paper}>8</Paper>
        </Grid>
      </Grid>
      <Grid container spacing={3}>
        <Grid item xs>
          <Paper className={classes.paper}>9</Paper>
        </Grid>
        <Grid item xs>
          <Paper className={classes.paper}>10</Paper>
        </Grid>
      </Grid>
    </div>
  );
}