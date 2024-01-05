import React from 'react';
import PropTypes from 'prop-types';
import { Grid, TextField } from '@mui/material';

import { MainCard } from '../ui-components/MainCard';
import { InputLabel } from '../ui-components/InputLabel';

const SubjectOfInformation = React.forwardRef(({ register, index, currentIndex, tab }, ref) => {
    return (
        <Grid item xs={12} lg={12} ref={ref}>
            <MainCard border>
                <Grid container spacing={2} alignItems="center">
                    <Grid item xs={12}>
                        <Grid container spacing={2} alignItems="center">
                            <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                                <InputLabel horizontal>Subject Of Information</InputLabel>
                            </Grid>
                            <Grid item xs={12} sm={9} lg={6}>
                                <TextField
                                    {...register(`test.${index}.item.${currentIndex}.${tab}.subject_of_information`)}
                                    fullWidth
                                    placeholder="Enter Subject Of Information"
                                />
                            </Grid>
                        </Grid>
                    </Grid>
                </Grid>
            </MainCard>
        </Grid>
    );
});
SubjectOfInformation.propTypes = {
    register: PropTypes.func,
    index: PropTypes.number,
    currentIndex: PropTypes.number,
    tab: PropTypes.string
};
export { SubjectOfInformation };
