import React from 'react';
import PropTypes from 'prop-types';
import { withRouter } from 'react-router-dom';
import { NavLink } from 'react-router-dom';

const link = `${process.env.PUBLIC_URL}/img/containers/sidebar/`;

const isLinkActive = (routes, path) => routes.filter((route) => route === '/dashboard' ? path === route : path.indexOf(route) > -1).length > 0;

const SidebarLink = ({
  title, icon, routes, path, onClick, onMouseEnter, onMouseLeave,
}) => (
  <NavLink
    to={routes[0]}
    onClick={onClick}
    isActive={() => isLinkActive(routes, path)}
    onMouseEnter={onMouseEnter}
    onMouseLeave={onMouseLeave}
    activeClassName={isLinkActive(routes, path) ? 'sidebar__link-active' : ''}
  >
    <li className="sidebar__link">
      {icon ? <img className="sidebar__link-icon" src={link + icon + '.svg'} alt={title} /> : ''}
      <p className="sidebar__link-title">
        {title}
      </p>
    </li>
  </NavLink>
);

SidebarLink.propTypes = {
  title: PropTypes.string.isRequired,
  icon: PropTypes.string,
  routes: PropTypes.array,
  path: PropTypes.string,
  onClick: PropTypes.func,
};

SidebarLink.defaultProps = {
  icon: '',
  routes: ['/'],
  path: '/',
  onClick: () => {},
};

export default withRouter(SidebarLink);
