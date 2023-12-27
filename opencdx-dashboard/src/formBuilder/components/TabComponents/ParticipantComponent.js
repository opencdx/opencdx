import React from 'react';
import { Divider, Grid, TextField } from '@mui/material';

import { MainCard } from '../ui-components/MainCard';
import { InputLabel } from '../ui-components/InputLabel';

export const ParticipantComponent = React.forwardRef(({ register, index, currentIndex, tab }, ref) => {
    return (
        <Grid item xs={12} lg={12} ref={ref}>
            <MainCard border>
                <Grid container spacing={2} alignItems="center">
                    <Grid item xs={12}>
                        <Grid container spacing={2} alignItems="center">
                            <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                                <InputLabel horizontal>ID</InputLabel>
                            </Grid>
                            <Grid item xs={12} sm={9} lg={6}>
                                <TextField
                                    {...register(`test.${index}.item.${currentIndex}.${tab}.id`)}
                                    fullWidth
                                    placeholder="Enter ID Value"
                                />
                            </Grid>

                            <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                                <InputLabel horizontal>Practitioner</InputLabel>
                            </Grid>
                            <Grid item xs={12} sm={9} lg={6}>
                                <TextField
                                    {...register(`test.${index}.item.${currentIndex}.${tab}.practitionerValue`)}
                                    fullWidth
                                    placeholder="Enter Practitioner Value"
                                />
                            </Grid>
                        </Grid>
                    </Grid>
                    <Divider />

                    <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                        <InputLabel horizontal>Code</InputLabel>
                    </Grid>
                    <Grid item xs={12} sm={9} lg={6}>
                        <TextField
                            {...register(`test.${index}.item.${currentIndex}.${tab}.code`)}
                            fullWidth
                            placeholder="Enter Code Value"
                        />
                    </Grid>
                </Grid>
            </MainCard>
        </Grid>
    );
});
