import React from 'react';
import { MeasureComponent } from '../TabComponents/MeasureComponent';

export const Time = React.forwardRef(({ control, register, index, currentIndex }, ref) => (
    <MeasureComponent {...{ control, register, index, currentIndex }} tab="time" ref={ref} />
));
