import React from 'react';
import PropTypes from 'prop-types';
import { Grid, InputLabel } from '@mui/material';

import { MainCard } from '../ui-components/MainCard';
import { Controller } from 'react-hook-form';
import { FormControl, MenuItem, Select } from '@mui/material';


const Type = React.forwardRef(({  index, currentIndex, control }, ref) => {
    return (
        <Grid item xs={12} lg={12} ref={ref}>
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
                                name={`test.${index}.item.${currentIndex}.typeValue`}
                                control={control}
                                defaultValue={10}
                                render={({ field }) => (
                                    <Select {...field}  >
                                        <MenuItem value={10}  defaultValue={10}>Performance </MenuItem>
                                        <MenuItem value={20}>Request</MenuItem>
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
    currentIndex: PropTypes.number
};
export { Type };
