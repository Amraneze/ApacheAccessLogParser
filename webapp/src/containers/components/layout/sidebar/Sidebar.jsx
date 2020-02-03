import React from 'react';
import PerfectScrollbar from 'react-perfect-scrollbar';
import classNames from 'classnames';
import SidebarContent from './SidebarContent';
import { SidebarProps } from '../../../../shared/prop-types/ReducerProps';

const icSideMenu = `${process.env.PUBLIC_URL}/img/containers/sidebar/ic_side_menu_submenu.svg`;
const icLogo = `${process.env.PUBLIC_URL}/img/logo/logo.svg`;
const icLogoFull = `${process.env.PUBLIC_URL}/img/logo/logo_white.svg`;

const Sidebar = ({ sidebar, changeSidebarVisibility, }) => {
  const sidebarClass = classNames({
    sidebar: true,
    'sidebar--show': sidebar.show,
    'sidebar--collapse': sidebar.collapse,
  });

  return (
    <div className={sidebarClass}>
      <img src={sidebar.collapse ? icLogo : icLogoFull} alt="logo" className={'sidebar__logo' + (!sidebar.collapse ? ' show' : '')}/>
      <button className="sidebar__back" />
      <button className="sidebar__button sidebar__button--desktop"
        onMouseEnter={() => changeSidebarVisibility(true)}
        onMouseLeave={() => changeSidebarVisibility(false)}
        onClick={changeSidebarVisibility}>
          <img src={icSideMenu} alt="Menu" className="sidebar__button-icon" />
          {!sidebar.collapse ? <span className="h3 h3__r__white sidebar__menu">Menu</span> : ''}
      </button>
      <PerfectScrollbar className="sidebar__scroll scroll" options={{suppressScrollX: true}}>
        <div className="sidebar__wrapper sidebar__wrapper--desktop">
          <SidebarContent onMouseEnter={(open) => changeSidebarVisibility(open)} />
        </div>
      </PerfectScrollbar>
    </div>
  );
};

Sidebar.propTypes = {
  sidebar: SidebarProps.isRequired,
};

export default Sidebar;
