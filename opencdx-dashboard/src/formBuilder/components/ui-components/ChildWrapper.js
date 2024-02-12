import React from 'react';
import PropTypes from 'prop-types';
import { useFieldArray } from 'react-hook-form';
import StatementTypes from './StatementTypes';
import OptionWrapper from './OptionWrapper';
import { ComponentID } from '../TabComponents/ComponentID';
import { AccordianWrapper } from './AccordianWrapper';
import { Grid, FormControl, Select, MenuItem, Typography } from '@mui/material';
import { useTheme } from '@mui/material/styles';
import { Controller } from 'react-hook-form';

const ChildWrapper = React.forwardRef(({ control, register }, ref) => {
    const { fields, remove } = useFieldArray({
        control,
        name: 'test'
    });
    const [rulesets] = React.useState([
        {
            ruleId: '1',
            type: 'Business Rule',
            category: 'Validation',
            description: 'Validate user responses'
        },
        {
            ruleId: '2',
            type: 'Authorization Rule',
            category: 'Access Control',
            description: 'Control access based on user responses'
        }
    ]);
    const theme = useTheme();
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
            <Grid
                container
                item
                xs={12}
                lg={12}
                mt={1}
                padding={3}
                alignItems="center"
                sx={{
                    border: '1px solid',
                    borderColor: theme.palette.mode === 'dark' ? theme.palette.dark.light + 15 : theme.palette.grey[200],
                    ':hover': {
                        boxShadow: theme.palette.mode === 'dark' ? '0 2px 14px 0 rgb(33 150 243 / 10%)' : '0 2px 14px 0 rgb(32 40 45 / 8%)'
                    }
                }}
            >
                <Grid item xs={12} sm={3} lg={2}>
                    <Typography variant="h5" gutterBottom>
                        Select a rule
                    </Typography>
                </Grid>
                <Grid item xs={12} sm={9} lg={10}>
                    <FormControl fullWidth>
                        <Controller
                            name={`test.rulesets`}
                            {...register(`test.rulesets`)}
                            control={control}
                            defaultValue={localStorage.getItem('anf-form') ? JSON.parse(localStorage.getItem('anf-form')).ruleset : ''}
                            render={({ field }) => (
                                <Select {...field} id={`test.rulesets`} fullWidth variant="outlined" size="small">
                                    {rulesets.map((ruleset) => (
                                        <MenuItem key={ruleset.ruleId} value={ruleset.ruleId}>
                                            {ruleset.type} - {ruleset.category} - {ruleset.description}
                                        </MenuItem>
                                    ))}
                                </Select>
                            )}
                        />
                    </FormControl>
                </Grid>
            </Grid>
        </div>
    );
});

ChildWrapper.propTypes = {
    register: PropTypes.func,
    control: PropTypes.object
};

export default ChildWrapper;
