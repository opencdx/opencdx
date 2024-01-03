import React from 'react';
import PropTypes from 'prop-types';
import { useFieldArray } from 'react-hook-form';
import StatementTypes from './StatementTypes';
import OptionWrapper from './OptionWrapper';
import { ComponentID } from '../TabComponents/ComponentID';
import { AccordianWrapper } from './AccordianWrapper';

const ChildWrapper = React.forwardRef(({ control, register }, ref) => {
    const { fields, remove } = useFieldArray({
        control,
        name: 'test'
    });
    return (
        <div className="wrapper" ref={ref}>
            {fields.map((item, index) => {
                return (
                    <AccordianWrapper key={index} title={index + 1 + '. ' + item.text + ' - ' + item.linkId} remove={() => remove(index)}>
                        <ComponentID {...{ control, register, index }} />
                        <StatementTypes {...{ control, register, index, item }} />
                        <OptionWrapper {...{ control, register, index, item }} />
                    </AccordianWrapper>
                );
            })}
        </div>
    );
});

ChildWrapper.propTypes = {
    register: PropTypes.func,
    control: PropTypes.object
};

export default ChildWrapper;
