/* eslint-disable no-return-assign */
import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import Sidebar from './sidebar/Sidebar';
import { changeSidebarVisibility } from '../../../redux/actions/SidebarActions';
import { SidebarProps } from '../../../shared/prop-types/ReducerProps';

class Layout extends Component {
  static propTypes = {
    dispatch: PropTypes.func.isRequired,
    sidebar: SidebarProps.isRequired,
  };

  changeSidebarVisibility = (open) => this.props.dispatch(changeSidebarVisibility(open));

  render() {
    const { sidebar } = this.props;
    const layoutClass = classNames({
      layout: true,
      'layout--collapse': sidebar.collapse
    });

    return (
      <div className={layoutClass}>
        <Sidebar sidebar={sidebar}
          changeSidebarVisibility={this.changeSidebarVisibility} />
      </div>
    );
  }
}

export default withRouter(connect(state => ({
  sidebar: state.sidebar,
}))(Layout));
