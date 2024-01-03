import React from 'react';
import PropTypes from 'prop-types';
import { ParticipantComponent } from '../TabComponents/ParticipantComponent';

const SubjectofRecord = React.forwardRef(({ register, index, currentIndex }, ref) => (
    <ParticipantComponent {...{ register, index, currentIndex }} tab="subjectOfReecord" ref={ref} />
));
SubjectofRecord.propTypes = {
    register: PropTypes.func,
    index: PropTypes.number,
    currentIndex: PropTypes.number
};
export { SubjectofRecord };
