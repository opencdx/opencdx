import React from 'react';
import PropTypes from 'prop-types';
import { SystemVariables } from '../ui-components/SystemVariables';
import { ParticipantComponent } from '../TabComponents/ParticipantComponent';

const SubjectofRecord = React.forwardRef(({ register, index, getValues, currentIndex }, ref) => {
    return (
        <>
           <SystemVariables index={index} currentIndex={currentIndex} getValues={getValues} tab="subjectOfRecord" />
            <ParticipantComponent {...{ register, index, currentIndex }} tab="subjectOfRecord" ref={ref} />
        </>
    );
});
SubjectofRecord.propTypes = {
    register: PropTypes.func,
    index: PropTypes.number,
    currentIndex: PropTypes.number,
    getValues: PropTypes.any
};
export { SubjectofRecord };
