import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { withRouter } from 'react-router-dom';
import SidebarLink from './SidebarLink';

class SidebarContent extends Component {

  hideSidebar = (open) => {
    this.props.onMouseEnter(open);
  };
  
  render() {
    const { location : { pathname } } = this.props;

    return (
      <div className="sidebar__content">
        <ul className="sidebar__block">
          <SidebarLink
            title="Tableau de bord"
            icon="ic_side_menu_dashboard"
            routes={["/"]}
            path={pathname}
            onMouseEnter={() => this.hideSidebar(true)}
            onMouseLeave={() => this.hideSidebar(false)}
          />
        </ul>
      </div>
    );
  }
}

SidebarContent.propTypes = {
  onMouseEnter: PropTypes.func.isRequired,
};

export default withRouter(SidebarContent);
