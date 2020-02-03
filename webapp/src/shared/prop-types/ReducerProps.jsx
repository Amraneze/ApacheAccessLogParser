import PropTypes from 'prop-types';

const { shape, } = PropTypes;

export const SidebarProps = shape({
  show: PropTypes.bool,
  collapse: PropTypes.bool,
  showProfile: PropTypes.bool,
});