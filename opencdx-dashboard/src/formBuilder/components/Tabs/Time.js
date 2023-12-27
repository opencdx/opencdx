import React from 'react';
import { MeasureComponent } from '../TabComponents/MeasureComponent';

const Time = React.forwardRef(({ control, register, index, currentIndex }, ref) => (
    <MeasureComponent {...{ control, register, index, currentIndex }} tab="time" ref={ref} />
));
Time.propTypes = {
    register: PropTypes.func,
    control: PropTypes.object,
    index: PropTypes.number,
    currentIndex: PropTypes.number
};
export { Time };
