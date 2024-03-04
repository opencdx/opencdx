import React from 'react';
import PropTypes from 'prop-types';
import { MeasureComponent } from '../TabComponents/MeasureComponent';
import { SystemVariables } from '../ui-components/SystemVariables';

const Time = React.forwardRef(({ control, register, index, currentIndex, getValues }, ref) => {
    return (
        <>
            <SystemVariables index={index} currentIndex={currentIndex} getValues={getValues} tab="time" />
            <MeasureComponent {...{ control, register, index, currentIndex }} tab="time" ref={ref} />
        </>
    );
});
Time.propTypes = {
    register: PropTypes.func,
    control: PropTypes.object,
    index: PropTypes.number,
    currentIndex: PropTypes.number,
    getValues: PropTypes.any
};
export { Time };
