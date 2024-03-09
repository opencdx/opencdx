import React from 'react';
import PropTypes from 'prop-types';
import { Grid, InputLabel } from '@mui/material';

import { MainCard } from '../ui-components/MainCard';
import { Controller } from 'react-hook-form';
import { FormControl, MenuItem, Select } from '@mui/material';
import { SystemVariables } from '../ui-components/SystemVariables';

const Type = React.forwardRef(({ index, currentIndex, control, getValues }, ref) => {
    return (
        <Grid item xs={12} lg={12} ref={ref}>
            <SystemVariables index={index} currentIndex={currentIndex} getValues={getValues} tab="type" />
            <MainCard border>
                <Grid container spacing={2} alignItems="center">
                    <Grid item xs={12}>
                        <Grid container spacing={2} alignItems="center">
                            <Grid item xs={12} sm={3} lg={4}>
                                <InputLabel horizontal>Type</InputLabel>
                            </Grid>
                            <Grid item xs={12} sm={9} lg={6}>
                                <FormControl fullWidth>
                                    <Controller
                                        name={`item.${index}.item.${currentIndex}.anfStatementConnector[0].anfStatement.type`}
                                        control={control}
                                        defaultValue={'PERFORMANCE'}
                                        render={({ field }) => (
                                            <Select {...field}>
                                                <MenuItem value={'PERFORMANCE'} defaultValue={'PERFORMANCE'}>
                                                    Performance
                                                </MenuItem>
                                                <MenuItem value={'REQUEST'}>Request</MenuItem>
                                            </Select>
                                        )}
                                    />
                                </FormControl>
                            </Grid>
                        </Grid>
                    </Grid>
                </Grid>
            </MainCard>
        </Grid>
    );
});
Type.propTypes = {
    register: PropTypes.func,
    index: PropTypes.number,
    currentIndex: PropTypes.number,
    control: PropTypes.object,
    getValues: PropTypes.func
};
export { Type };
