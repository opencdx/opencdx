import React from 'react';
import PropTypes from 'prop-types';
import { ParticipantComponent } from '../TabComponents/ParticipantComponent';

export const Authors = React.forwardRef(({ control, register, index, currentIndex }, ref) => (
    <ParticipantComponent {...{ control, register, index, currentIndex }} tab="authors" ref={ref} />
));
Authors.propTypes = {
    register: PropTypes.func,
    control: PropTypes.object,
    index: PropTypes.number,
    currentIndex: PropTypes.number
};
