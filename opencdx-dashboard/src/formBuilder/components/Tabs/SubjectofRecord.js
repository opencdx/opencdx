import React from 'react';
import { ParticipantComponent } from '../TabComponents/ParticipantComponent';

export const SubjectofRecord = React.forwardRef(({ register, index, currentIndex }, ref) => (
    <ParticipantComponent {...{ register, index, currentIndex }} tab="subjectOfReecord" ref={ref} />
));
