import changeSearch from './changeSearch'
import navigation from './navigation'
const { combineReducers } = require("redux");

const APP = combineReducers({
    changeSearch,
    navigation,
})

export default APP;