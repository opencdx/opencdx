import React from 'react';
import { FormControl, Grid, MenuItem, Select, TextField } from '@mui/material';

import { MainCard } from 'ui-components/MainCard';
import { InputLabel } from 'ui-components/InputLabel';

export const AssociatedStatements = React.forwardRef((props, ref) => {
    return (
        <Grid item xs={12} lg={12}>
            <MainCard ref={ref}>
                <Grid container spacing={2} alignItems="center">
                    <Grid item xs={12}>
                        <Grid container spacing={2} alignItems="center">
                            <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                                <InputLabel horizontal>Statement Type</InputLabel>
                            </Grid>
                            <Grid item xs={12} sm={9} lg={6}>
                                <FormControl fullWidth>
                                    <Select labelId="demo-simple-select-label" id="demo-simple-select">
                                        <MenuItem value={10}>Main Statement</MenuItem>
                                        <MenuItem value={20}>Associated Statement</MenuItem>
                                        <MenuItem value={30}>Associated Question</MenuItem>
                                        <MenuItem value={40}>Not Applicable</MenuItem>
                                    </Select>
                                </FormControl>
                            </Grid>
                            <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                                <InputLabel horizontal>UUID</InputLabel>
                            </Grid>
                            <Grid item xs={12} sm={9} lg={6}>
                                <TextField fullWidth placeholder="Enter UUID Value" />
                            </Grid>
                        </Grid>
                    </Grid>
                </Grid>
            </MainCard>
        </Grid>
    );
});
