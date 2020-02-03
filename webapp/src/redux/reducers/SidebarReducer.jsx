import {
  CHANGE_SIDEBAR_VISIBILITY
} from '../constants/SidebarConstants';

const initialState = {
  show: false,
  collapse: getSidebarCollapseState(),
};

function setSidebarCollapsed(isCollapsed) {
    localStorage.setItem('sidebarCollapsed', isCollapsed);
}

function getSidebarCollapseState() {
  return !localStorage.getItem('sidebarCollapsed') ? true : 
    JSON.parse(localStorage.getItem('sidebarCollapsed'));
}

export default function(state = initialState, action) {
  if (action.type === CHANGE_SIDEBAR_VISIBILITY) {
    setSidebarCollapsed(!action.open);
    return {...state, collapse: !action.open};
  } else {
    return state;
  }
}
