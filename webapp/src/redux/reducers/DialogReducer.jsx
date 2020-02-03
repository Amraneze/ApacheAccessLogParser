import { DialogConstants } from '../constants/';

const initialState = {
  show: false,
  props: {},
};

export default function (state = initialState, action) {
  if (action.type === DialogConstants.CHANGE_DIALOG_VISIBILITY) {
    return {show: action.show, props: action.props};
  } else {
    return state;
  }
}
