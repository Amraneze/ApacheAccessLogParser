import React from 'react';
import PropTypes from 'prop-types';

const Textarea = ({
  classes, value, splitter
}) => (value.split(splitter).map((sentence, index) => (<span key={index} className={classes}>{sentence}<br/></span>)));

Textarea.defaultProps = {
    splitter: '\n',
    value: '',
    classes: '',
};

Textarea.propTypes = {
    classes: PropTypes.string,
    value: PropTypes.string.isRequired,
    splitter: PropTypes.string,
};

export default Textarea;
