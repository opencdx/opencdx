import React from 'react';
import PropTypes from 'prop-types';

import { MeasureComponent } from '../TabComponents/MeasureComponent';
import { ParticipantComponent } from '../TabComponents/ParticipantComponent';
import { FormControl, Grid, MenuItem, Select, TextField } from '@mui/material';
import { MainCard } from '../ui-components/MainCard';
import { InputLabel } from '../ui-components/InputLabel';
import { Controller } from 'react-hook-form';

import { systemVariables, statementType } from '../../store/constant';
import { SystemVariables } from '../ui-components/SystemVariables';
import { useAnfFormStore } from '../../utils/useAnfFormStore';

export const CircumstanceChoice = React.forwardRef(({ control, register, index, currentIndex, getValues }, ref) => {
    const { formData } = useAnfFormStore();
    const componentType = [statementType.MAIN, statementType.ASSOCIATED].includes(formData.item[index]?.componentType);

    return (
        <Grid item xs={12} lg={12} ref={ref}>
            <SystemVariables index={index} currentIndex={currentIndex} getValues={getValues} tab="circumstanceChoice" />
            <MainCard>
                <Grid container spacing={2} alignItems="center">
                    <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                        <InputLabel horizontal>Circumstance Type</InputLabel>
                    </Grid>
                    <Grid item xs={12} sm={9} lg={8}>
                        <FormControl fullWidth>
                            <Controller
                                name={`item.${index}.item.${currentIndex}.anfStatementConnector[0].anfStatement.circumstanceChoice.circumstanceType`}
                                control={control}
                                defaultValue={'PERFORMANCE_CIRCUMSTANCE'}
                                render={({ field }) => (
                                    <Select {...field}>
                                        <MenuItem value={'PERFORMANCE_CIRCUMSTANCE'} defaultValue={10}>
                                            Performance Circumstance
                                        </MenuItem>
                                        <MenuItem value={'REQUEST_CIRCUMSTANCE'}>Request Circumstance</MenuItem>
                                        <MenuItem value={'NARRATIVE_CIRCUMSTANCE'}>Narrative Circumstance</MenuItem>
                                    </Select>
                                )}
                            />
                        </FormControl>
                    </Grid>
                    <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                        <InputLabel horizontal>Status</InputLabel>
                    </Grid>
                    <Grid item xs={12} sm={9} lg={8}>
                        {componentType ? (
                            <TextField
                                {...register(`item.${index}.item.${currentIndex}..anfStatementConnector[0].anfStatement.circumstanceChoice.status`)}
                                fullWidth
                                placeholder="Enter Type Information"
                                defaultValue={JSON.stringify(systemVariables['status'])}
                            />
                        ) : (
                            <FormControl fullWidth>
                                <Controller
                                    name={`item.${index}.item.${currentIndex}.status`}
                                    control={control}
                                    render={({ field }) => (
                                        <Select {...field} id={`item.${index}.item.${currentIndex}.anfStatementConnector[0].anfStatement.circumstanceChoice.status`}>
                                            <MenuItem value={10}>On Hold</MenuItem>
                                            <MenuItem value={20}>Completed</MenuItem>
                                            <MenuItem value={30}>Needed</MenuItem>
                                            <MenuItem value={40}>Rejected</MenuItem>
                                        </Select>
                                    )}
                                />
                            </FormControl>
                        )}
                    </Grid>

                    <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                        <InputLabel horizontal>Result</InputLabel>
                    </Grid>
                    {<MeasureComponent {...{ control, register, index, currentIndex }} tab="circumstanceChoice.result" />}
                    <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                        <InputLabel horizontal>Health Risks</InputLabel>
                    </Grid>
                    <Grid item xs={12} sm={9} lg={8}>
                        <FormControl fullWidth>
                            {componentType ? (
                                <TextField
                                    {...register(`item.${index}.item.${currentIndex}.anfStatementConnector[0].anfStatement.circumstanceChoice.healthRisk`)}
                                    fullWidth
                                    placeholder="Enter Health Risk Information"
                                    defaultValue={systemVariables['circumstanceChoice'][0].healthRisk.replace('XXXXX', '')}
                                />
                            ) : (
                                <TextField
                                        {...register(`item.${index}.item.${currentIndex}.anfStatementConnector[0].anfStatement.circumstanceChoice.healthRisk`)}
                                    fullWidth
                                    placeholder="Enter Health Risk Information"
                                />
                            )}
                        </FormControl>
                    </Grid>
                    <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                        <InputLabel horizontal>Normal Range</InputLabel>
                    </Grid>
                    {<MeasureComponent {...{ control, register, index, currentIndex }} tab="circumstanceChoice.normalRange" />}
                    <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important', marginTop: 10 } }}>
                        <InputLabel horizontal>Circumstance</InputLabel>
                    </Grid>
                    {<ParticipantComponent {...{ control, register, index, currentIndex }} tab="rangeParticipant" />}

                    <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important', marginTop: 10 } }}>
                        <InputLabel horizontal>Timing</InputLabel>
                    </Grid>
                    {<MeasureComponent {...{ control, register, index, currentIndex }} tab="circumstanceChoice.timing" />}
                    <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important', marginTop: 10 } }}>
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
    currentIndex: PropTypes.number,
    getValues: PropTypes.func
};
