import React from 'react';
import PropTypes from 'prop-types';
import { makeStyles, useTheme } from '@material-ui/core/styles';
import Drawer from '@material-ui/core/Drawer';
import CssBaseline from '@material-ui/core/CssBaseline';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import List from '@material-ui/core/List';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import { Accessibility, AccountTree, AddBox, Home } from '@material-ui/icons';
import { Link, Route } from 'react-router-dom';
import { Hidden } from '@material-ui/core';
import SearchHuman from './SearchHuman';
import HomeP from './Home';
import Department from './Department';
import Manage from './Manage';

const drawerWidth = 240;

const useStyles = makeStyles((theme) => ({
    root: {
        display: 'flex',
      },
      drawer: {
        [theme.breakpoints.up('sm')]: {
          width: drawerWidth,
          flexShrink: 0,
        },
      },
      appBar: {
        [theme.breakpoints.up('sm')]: {
          width: `calc(100% - ${drawerWidth}px)`,
          marginLeft: drawerWidth,
        },
      },
      menuButton: {
        marginRight: theme.spacing(2),
        [theme.breakpoints.up('sm')]: {
          display: 'none',
        },
      },
      // necessary for content to be below app bar
      toolbar: theme.mixins.toolbar,
      drawerPaper: {
        width: drawerWidth,
      },
      content: {
        flexGrow: 1,
        padding: theme.spacing(3),
      },
    }));

function PersistentDrawerLeft(props) {
    const { window } = props;
    const classes = useStyles();
    const theme = useTheme();
    const [mobileOpen, setMobileOpen] = React.useState(false);
    const [selectedIndex, setSelectedIndex] = React.useState(0);

    const handleDrawerToggle = () => {
        setMobileOpen(!mobileOpen);
    };

    const handleListItemClick = (event, index) => {
        setSelectedIndex(index);
    };

    const drawer = (
        <div>
        <div className={classes.toolbar} />
        <Divider />
        <List>      
            <ListItem 
            component={Link} to={'/'}
            button
            selected={selectedIndex === 0}
            onClick={(event) => handleListItemClick(event, 0)}
            >
                <ListItemIcon color='primary.main'>
                    <Home/>
                </ListItemIcon>                
                <ListItemText primary="Home"/>                
            </ListItem>  

            <ListItem 
            component={Link} to={'/SearchHuman'}
            button
            selected={selectedIndex === 1}
            onClick={(event) => handleListItemClick(event, 1)}
            >
                <ListItemIcon color='primary.main'>
                    <Accessibility/>
                </ListItemIcon>                
                <ListItemText primary="사원조회"/>                
            </ListItem>      
            
            <ListItem 
            component={Link} to={'/Department'}
            button
            selected={selectedIndex === 2}
            onClick={(event) => handleListItemClick(event, 2)}>
                <ListItemIcon>
                    <AccountTree/>
                </ListItemIcon>
                <ListItemText primary="부서조회"/>                
            </ListItem>
           
            <ListItem 
            component={Link} to={'/Manage'}
            button
            selected={selectedIndex === 3}
            onClick={(event) => handleListItemClick(event, 3)}>
                <ListItemIcon>
                    <AddBox/>
                </ListItemIcon>
                <ListItemText primary="사원관리"/>                
            </ListItem>
      
        </List>
        <Divider />
        </div>
    );

    const container = window !== undefined ? () => window().document.body : undefined;

  return (
    <div className={classes.root}>
      <CssBaseline />
      <AppBar position="fixed" className={classes.appBar}>
        <Toolbar>
          <IconButton
            color="inherit"
            aria-label="open drawer"
            edge="start"
            onClick={handleDrawerToggle}
            className={classes.menuButton}
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" noWrap>
            Employees Management System
          </Typography>
        </Toolbar>
      </AppBar>

      <nav className={classes.drawer} aria-label="mailbox folders">
        {/* The implementation can be swapped with js to avoid SEO duplication of links. */}
        <Hidden smUp implementation="css">
          <Drawer
            container={container}
            variant="temporary"
            anchor={theme.direction === 'rtl' ? 'right' : 'left'}
            open={mobileOpen}
            onClose={handleDrawerToggle}
            classes={{
              paper: classes.drawerPaper,
            }}
            ModalProps={{
              keepMounted: true, // Better open performance on mobile.
            }}
          >
            {drawer}
          </Drawer>
        </Hidden>
        <Hidden xsDown implementation="css">
          <Drawer
            classes={{
              paper: classes.drawerPaper,
            }}
            variant="permanent"
            open
          >
            {drawer}
          </Drawer>
        </Hidden>
      </nav>
      <main className={classes.content}>
        <div className={classes.toolbar} />
        <Route exact path="/" component={HomeP} />
        <Route path="/SearchHuman" component={SearchHuman} />         
        <Route path="/Home" component={HomeP} />         
        <Route path="/Department" component={Department} />  
        <Route path="/Manage" component={Manage} />          
      </main>      
    </div>
  );
}

PersistentDrawerLeft.propTypes = {
    /**
     * Injected by the documentation to work in an iframe.
     * You won't need it on your project.
     */
    window: PropTypes.func,
};

export default PersistentDrawerLeft;
    