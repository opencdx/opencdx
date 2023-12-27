import React from 'react';
import { FormControl, Radio, RadioGroup, Divider, Grid, TextField, FormControlLabel } from '@mui/material';
import { MainCard } from '../ui-components/MainCard';
import { InputLabel } from '../ui-components/InputLabel';
import { Controller } from 'react-hook-form';

export const MeasureComponent = React.forwardRef(({ register, index, currentIndex, tab, control }, ref) => {
    return (
        <Grid item xs={12} lg={12} ref={ref}>
            <MainCard border>
                <Grid container spacing={2} alignItems="center">
                    <Grid item xs={12}>
                        <Grid container spacing={1} alignItems="center">
                            <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                                <InputLabel horizontal>Lower Bound</InputLabel>
                            </Grid>
                            <Grid item xs={12} sm={9} lg={6}>
                                <TextField
                                    fullWidth
                                    type="number"
                                    InputProps={{
                                        inputProps: { min: 0 }
                                    }}
                                    {...register(`test.${index}.item.${currentIndex}.${tab}.lowerBound`)}
                                    placeholder="Enter Lower Bound Value"
                                />
                            </Grid>
                            <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                                <InputLabel horizontal>Include Lower Bound</InputLabel>
                            </Grid>
                            <Grid item xs={12} sm={9} lg={6}>
                                <FormControl>
                                    <Controller
                                        control={control}
                                        {...register(`test.${index}.item.${currentIndex}.${tab}.lowerBoundOptions`)}
                                        render={({ field }) => (
                                            <RadioGroup
                                                row
                                                aria-label="lowerBoundOptions"
                                                name="lowerBoundOptions"
                                                {...field}
                                                onChange={(e) => {
                                                    field.onChange(e.target.value);
                                                }}
                                            >
                                                <FormControlLabel value="yes" control={<Radio />} label="Yes" />
                                                <FormControlLabel value="no" control={<Radio />} label="No" />
                                                <FormControlLabel value="not" control={<Radio />} label="Not Answered" />
                                            </RadioGroup>
                                        )}
                                    />
                                </FormControl>
                            </Grid>
                            <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                                <InputLabel horizontal>Semantic</InputLabel>
                            </Grid>
                            <Grid item xs={12} sm={9} lg={6}>
                                <TextField
                                    {...register(`test.${index}.item.${currentIndex}.${tab}.sematic`)}
                                    fullWidth
                                    placeholder="Enter Semantic Value"
                                />
                            </Grid>
                        </Grid>
                    </Grid>
                    <Divider />
                    <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                        <InputLabel horizontal>Resolution </InputLabel>
                    </Grid>
                    <Grid item xs={12} sm={8} lg={6}>
                        <TextField
                            {...register(`test.${index}.item.${currentIndex}.${tab}.resolution`)}
                            fullWidth
                            placeholder="Enter Resolution"
                        />
                    </Grid>
                    <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                        <InputLabel horizontal>Upper Bound</InputLabel>
                    </Grid>
                    <Grid item xs={12} sm={9} lg={6}>
                        <TextField
                            type="number"
                            {...register(`test.${index}.item.${currentIndex}.${tab}.upperBound`)}
                            fullWidth
                            placeholder="Enter Upper Bound Value"
                        />
                    </Grid>
                    <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                        <InputLabel horizontal>Include Upper Bound</InputLabel>
                    </Grid>
                    <Grid item xs={12} sm={9} lg={6}>
                        <FormControl>
                            <Controller
                                control={control}
                                {...register(`test.${index}.item.${currentIndex}.${tab}.upperBoundOptions`)}
                                render={({ field }) => (
                                    <RadioGroup
                                        row
                                        aria-label="upperBoundOptions"
                                        name="upperBoundOptions"
                                        {...field}
                                        onChange={(e) => {
                                            field.onChange(e.target.value);
                                        }}
                                    >
                                        <FormControlLabel value="yes" control={<Radio />} label="Yes" />
                                        <FormControlLabel value="no" control={<Radio />} label="No" />
                                        <FormControlLabel value="not" control={<Radio />} label="Not Answered" />
                                    </RadioGroup>
                                )}
                            />
                        </FormControl>
                    </Grid>
                </Grid>
            </MainCard>
        </Grid>
    );
});
