import React from 'react';
import PropTypes from 'prop-types';
import { ParticipantComponent } from '../TabComponents/ParticipantComponent';
import { SystemVariables } from '../ui-components/SystemVariables';

const Authors = React.forwardRef(({ control, register, index, currentIndex, getValues }, ref) => {
   
    return (
        <>
            <SystemVariables index={index} currentIndex={currentIndex} getValues={getValues} tab="authors" />
            <ParticipantComponent {...{ control, register, index, currentIndex }} tab="authors" ref={ref} />
        </>
    );
});
Authors.propTypes = {
    register: PropTypes.func,
    control: PropTypes.object,
    index: PropTypes.number,
    currentIndex: PropTypes.number,
    getValues: PropTypes.func
};

export { Authors };
