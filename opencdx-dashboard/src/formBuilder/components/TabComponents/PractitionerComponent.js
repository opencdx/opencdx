import React from 'react';
import { FormControl, Grid, MenuItem, Select } from '@mui/material';
import { MainCard } from '../ui-components/MainCard';

export const PractitionerComponent = React.forwardRef((props, ref) => {
    return (
        <Grid item xs={12} lg={12}>
            <MainCard>
                <Grid container spacing={2} alignItems="center">
                    <Grid item xs={12} sm={12} lg={12} container>
                        <Grid item xs={12} sm={4} lg={4}>
                            <FormControl fullWidth>
                                <Select ref={ref} labelId="demo-simple-select-label" id="demo-simple-select" label="Practitioner Id">
                                    <MenuItem value={10}>{'{{practitioner_id}}'}</MenuItem>
                                </Select>
                            </FormControl>
                        </Grid>
                        <Grid item xs={12} sm={4} lg={4}>
                            <FormControl fullWidth>
                                <Select labelId="demo-simple-select-label" id="demo-simple-select" label="Practitioner Name">
                                    <MenuItem value={10}>{'{{practitioner_name}}'}</MenuItem>
                                </Select>
                            </FormControl>
                        </Grid>
                        <Grid item xs={12} sm={4} lg={4}>
                            <FormControl fullWidth>
                                <Select labelId="demo-simple-select-label" id="demo-simple-select" label="Code">
                                    <MenuItem value={10}>{'{{practitioner_codes}}'}</MenuItem>
                                </Select>
                            </FormControl>
                        </Grid>
                        <Grid item xs={12} sm={12} lg={12}>
                            <FormControl fullWidth>
                                <Select labelId="demo-simple-select-label" id="demo-simple-select">
                                    <MenuItem value={10}>On examination - Systolic blood pressure reading</MenuItem>
                                </Select>
                            </FormControl>
                        </Grid>
                    </Grid>
                </Grid>
            </MainCard>
        </Grid>
    );
});
