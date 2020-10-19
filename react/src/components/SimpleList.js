import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import { Accessibility, AccountTree, AddBox } from '@material-ui/icons';
import { ListItemIcon } from '@material-ui/core';
import ListSubheader from '@material-ui/core/ListSubheader';
import Collapse from '@material-ui/core/Collapse';
import ExpandLess from '@material-ui/icons/ExpandLess';
import ExpandMore from '@material-ui/icons/ExpandMore';

const useStyles = makeStyles(theme => ({
    root: {
        width: '100%',
        maxWidth: 200,
        backgroundColor: theme.palette.background.paper,
        minHeight: 'calc(100vh - 64px)',
        borderRight: '1px solid #ddd',                   
    },
    nested: {
        paddingLeft: theme.spacing(4),
    },
}));

export default function SimpleList() {
    const classes = useStyles();

    const [selectedIndex, setSelectedIndex] = React.useState(0);
    const [open, setOpen] = React.useState(false);

    const handleClick = () => {
        setOpen(!open);
      };

    const handleListItemClick = (event, index) => {
      setSelectedIndex(index);
    };

    return (
        <List component="nav" 
        aria-labelledby="nested-list-subheader"
         subheader={
        <ListSubheader component="div" id="nested-list-subheader">
          <h1>MENU</h1>
        </ListSubheader>
        }
        className={classes.root}  
                
        >
            <ListItem button
            selected={selectedIndex === 0}
            onClick={(event) => handleListItemClick(event, 0)}
            >
                <ListItemIcon color='primary.main'>
                    <Accessibility/>
                </ListItemIcon>
                
                <ListItemText primary="사원 조회"/>
                
            </ListItem>

            <ListItem button
            selected={selectedIndex === 1}
            onClick={(event) => handleListItemClick(event, 1)}>
                <ListItemIcon>
                    <AccountTree/>
                </ListItemIcon>
                <ListItemText primary="부서 조회"/>
                
            </ListItem>

            <ListItem button            
            onClick={handleClick}>
                <ListItemIcon>
                    <AddBox/>
                </ListItemIcon>                
                <ListItemText primary="사원 관리"/>        
                {open ? <ExpandLess /> : <ExpandMore />}       
            </ListItem>
            <Collapse in={open} timeout="auto" unmountOnExit>
                <List component="div" disablePadding>
                    <ListItem button 
                    selected={selectedIndex === 2}
                    onClick={(event) => handleListItemClick(event, 2)}
                    className={classes.nested}>
                        <ListItemIcon>
                        <AddBox/>
                        </ListItemIcon>
                        <ListItemText primary="변경" />
                    </ListItem>
                </List>
            </Collapse>
        </List>
    );
}