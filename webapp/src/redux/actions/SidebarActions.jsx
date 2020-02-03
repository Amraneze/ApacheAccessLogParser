import { SidebarConstants } from '../constants/';

export function changeSidebarVisibility(open) {
  return {
    type: SidebarConstants.CHANGE_SIDEBAR_VISIBILITY,
    open: open,
  };
}

