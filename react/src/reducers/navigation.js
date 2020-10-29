import { NAVIGATION } from '../actions'

const initState = 0;

const navigation = (index = initState, action) => {
    switch(action.type) {
        case NAVIGATION:
            return index = action.index;                
        default:
            return index;
    }
}

export default navigation;