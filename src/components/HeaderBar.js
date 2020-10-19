import React from 'react'
import AppBar from '@material-ui/core/AppBar'
import Toolbar from '@material-ui/core/Toolbar'
import Typography from '@material-ui/core/Typography'
import {makeStyles} from '@material-ui/core/styles';

const useStyles = makeStyles(theme => ({
    root: {
        padding: 30,
    },
}));

export default function HeaderBar() {
    const classes = useStyles();
    return (
        <div className={classes.className}>
            <AppBar position="static">
                <Toolbar>
                    <Typography variant="title" color="inherit">
                        <h3>Human Management System</h3>
                    </Typography>
                </Toolbar>
            </AppBar>
        </div>
    );
}