import React from "react";
import {makeStyles} from '@material-ui/core/styles';
import Grid from "@material-ui/core/Grid";
import Card from "@material-ui/core/Card";
import Typography from "@material-ui/core/Typography";
import CardActionArea from "@material-ui/core/CardActionArea";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
import TrendingFlat from '@material-ui/icons/TrendingFlat';
import { Icon } from "@material-ui/core";

const useStyles = makeStyles((theme) => ({
    root: {
        padding: '0',
    },    
}));

const posts = [
    {
        title: "사원 조회",
        excerpt: "사원을 조회합니다.",
        image: "account_box"
    },

    {
        title: "부서 조회",
        excerpt: "부서 인원을 조회합니다.",
        image: "account_tree"
    },

    {
        title: "사원 관리",
        excerpt: "신입, 부서 이동, 퇴사 등 사원 관리 메뉴입니다.",
        image: "add_box"
    }
    
]

function Posts(props) {
    const classes = useStyles();

    return (                
        <div className={classes.root} >
            <Grid container spacing={3} direction="row" justify="center" alignItems="stretch">
                {posts.map(post => (
                    <Grid item xs={6} sm={3} md={12}  key={post.title}>
                        <Card>
                            <CardActionArea>                                                                
                                <CardContent>
                                    <Icon fontSize="large">{post.image}</Icon>        
                                    <Typography variant="subtitle2" color="textSecondary" gutterBottom>
                                        HMS
                                    </Typography>
                                    <Typography variant="h5" component="h2" gutterBottom>
                                        {post.title}
                                    </Typography>
                                    <Typography variant="body2" component="p">{post.excerpt}</Typography>
                                </CardContent>
                            </CardActionArea>
                            <CardActions>
                                <Button size="small" color="primary">
                                    메뉴로 이동 <TrendingFlat/>
                                </Button>
                            </CardActions>
                        </Card>
                    </Grid>
                ))}
            </Grid>
        </div>
    );
}

export default Posts;