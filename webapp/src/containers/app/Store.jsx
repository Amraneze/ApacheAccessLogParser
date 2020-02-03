import { combineReducers, createStore } from 'redux';
import { reducer as reduxFormReducer } from 'redux-form';
import { SidebarReducer, DialogReducer } from '../../redux/reducers/';

const reducer = combineReducers({
  form: reduxFormReducer,
  sidebar: SidebarReducer,
  dialog: DialogReducer,
});

const store = createStore(reducer);

export default store;
