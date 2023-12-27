import React from 'react';
import { ParticipantComponent } from '../TabComponents/ParticipantComponent';

export const Authors = React.forwardRef(({ control, register, index, currentIndex }, ref) => (
    <ParticipantComponent {...{ control, register, index, currentIndex }} tab="authors" ref={ref} />
));
