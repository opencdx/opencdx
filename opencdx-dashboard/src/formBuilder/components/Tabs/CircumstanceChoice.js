import React from 'react';
import PropTypes from 'prop-types';

import { MeasureComponent } from '../TabComponents/MeasureComponent';
import { ParticipantComponent } from '../TabComponents/ParticipantComponent';
import { FormControl, Grid, MenuItem, Select, TextField } from '@mui/material';
import { MainCard } from '../ui-components/MainCard';
import { InputLabel } from '../ui-components/InputLabel';
import { Controller } from 'react-hook-form';
import { ExpressionType } from '../TabComponents/ExpressionType';

import {systemVariables} from '../../store/constant';


export const CircumstanceChoice = React.forwardRef(({ control, register, index, currentIndex }, ref) => {
    const formData = JSON.parse(localStorage.getItem('anf-form'));
    const componentType = ['main_anf_statement', 'associated_anf_statement'].includes(formData.item[index]?.componentType);

    return (
        <Grid item xs={12} lg={12} ref={ref}>
            <MainCard>
                <Grid container spacing={2} alignItems="center">
                    <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                        <InputLabel horizontal>Circumstance Type</InputLabel>
                    </Grid>
                    <Grid item xs={12} sm={9} lg={6}>
                        <FormControl fullWidth>
                            <Controller
                                name={`test.${index}.item.${currentIndex}.type`}
                                control={control}
                                render={({ field }) => (
                                    <Select {...field} id={`test.${index}.item.${currentIndex}.type`}>
                                        <MenuItem value={10}>Performance Circumstance</MenuItem>
                                        <MenuItem value={20}>Request Circumstance</MenuItem>
                                        <MenuItem value={30}>Narrative Circumstance</MenuItem>
                                    </Select>
                                )}
                            />
                        </FormControl>
                    </Grid>
                    <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                        <InputLabel horizontal>Status</InputLabel>
                    </Grid>
                    <Grid item xs={12} sm={9} lg={6}>
                        <FormControl fullWidth>
                            <Controller
                                name={`test.${index}.item.${currentIndex}.status`}
                                control={control}
                                render={({ field }) => (
                                    <Select {...field} id={`test.${index}.item.${currentIndex}.status`}>
                                        <MenuItem value={10}>On Hold</MenuItem>
                                        <MenuItem value={20}>Completed</MenuItem>
                                        <MenuItem value={30}>Needed</MenuItem>
                                        <MenuItem value={40}>Rejected</MenuItem>
                                    </Select>
                                )}
                            />
                        </FormControl>
                    </Grid>
                    <Grid item xs={12} sm={3} lg={12} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                    <ExpressionType register={register} index={index} currentIndex={currentIndex} tab='status' ref={ref}/>
                                    
                    </Grid>
                        <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                        <InputLabel horizontal>Result</InputLabel>
                    </Grid>
                    {<MeasureComponent {...{ control, register, index, currentIndex }} tab="result" />}
                    <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                        <InputLabel horizontal>Health Risks</InputLabel>
                    </Grid>
                    <Grid item xs={12} sm={9} lg={6}>
                        <FormControl fullWidth>
                            {componentType ?
                            <TextField
                                {...register(`test.${index}.item.${currentIndex}.healthRisk`)}
                                fullWidth
                                placeholder="Enter Health Risk Information"
                                value={systemVariables['circumstanceChoice'][0].healthRisk}
                                />:
                            <TextField
                                {...register(`test.${index}.item.${currentIndex}.healthRisk`)}
                                fullWidth
                                placeholder="Enter Health Risk Information"
                            />}
                        </FormControl>
                    </Grid>
                    <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                        <InputLabel horizontal>Normal Range</InputLabel>
                    </Grid>
                    {<MeasureComponent {...{ control, register, index, currentIndex }} tab="rangeMeasure" />}
                    <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                        <InputLabel horizontal>Circumstance</InputLabel>
                    </Grid>
                    {<ParticipantComponent {...{ control, register, index, currentIndex }} tab="rangeParticipant" />}

                    <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                        <InputLabel horizontal>Timing</InputLabel>
                    </Grid>
                    {<MeasureComponent {...{ control, register, index, currentIndex }}  tab="timingMeasure"/>}
                    <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                        <InputLabel horizontal>Participant</InputLabel>
                    </Grid>
                    {<ParticipantComponent {...{ control, register, index, currentIndex }} tab={'rangeParticipant'} />}
                </Grid>
            </MainCard>
        </Grid>
    );
});

CircumstanceChoice.propTypes = {
    register: PropTypes.func,
    control: PropTypes.func,
    index: PropTypes.number,
    currentIndex: PropTypes.number
};
