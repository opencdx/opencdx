import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import { FormControl, Radio, RadioGroup, Divider, Grid, TextField, FormControlLabel } from '@mui/material';
import { Controller } from 'react-hook-form';
import { MainCard } from '../ui-components/MainCard';
import { InputLabel } from '../ui-components/InputLabel';

import {systemVariables} from '../../store/constant';

export const MeasureComponent = React.forwardRef(({ register, index,  currentIndex, tab, control }, ref) => {
    const formData = JSON.parse(localStorage.getItem('anf-form'));
    const componentType = ['main_anf_statement', 'associated_anf_statement'].includes(formData.item[index]?.componentType) && !['timingMeasure','rangeMeasure','result'].includes(tab);

    return (
        <Grid item xs={12} lg={12} ref={ref}>
            <MainCard border>
                <Grid container spacing={2} alignItems="center">
                    <Grid item xs={12}>
                        <Grid container spacing={1} alignItems="center">
                            <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                                <InputLabel horizontal>Lower Bound</InputLabel>
                            </Grid>
                            <Grid item xs={12} sm={9} lg={8}>
                                {componentType ?
                                <TextField
                                    {...register(`test.${index}.item.${currentIndex}.${tab}.lowerBound`)}
                                    fullWidth
                                    type='text'
                                    value={systemVariables[tab].lowerBound}
                                    placeholder="Enter Lower Bound Value"
                                    />
                                    :
                                    <TextField
                                    {...register(`test.${index}.item.${currentIndex}.${tab}.lowerBound`)}
                                    fullWidth
                                    type={'number'}
                                    InputProps={{
                                        inputProps: { min: 0 }
                                    }}                               
                                    placeholder="Enter Lower Bound Value"

                                />}
                            </Grid>
                            <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                                <InputLabel horizontal>Include Lower Bound</InputLabel>
                            </Grid>
                            <Grid item xs={12} sm={9} lg={8}>
                                <FormControl>
                                    <Controller
                                        control={control}
                                        {...register(`test.${index}.item.${currentIndex}.${tab}.lowerBoundOptions`)}
                                        render={({ field }) => componentType?(
                                            <RadioGroup
                                                row
                                                aria-label="lowerBoundOptions"
                                                name="lowerBoundOptions"
                                                {...field}
                                                onChange={(e) => {
                                                    field.onChange(e.target.value);
                                                }}
                                                value={systemVariables[tab].lowerBoundOptions}
                                            >
                                                <FormControlLabel value="yes" control={<Radio />} label="Yes" />
                                                <FormControlLabel value="no" control={<Radio />} label="No" />
                                                <FormControlLabel value="not" control={<Radio />} label="Not Answered" />
                                            </RadioGroup>
                                        ):(<RadioGroup
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
                                        </RadioGroup>)}
                                    />
                                </FormControl>
                            </Grid>
                            <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                                <InputLabel horizontal>Semantic</InputLabel>
                            </Grid>
                            <Grid item xs={12} sm={9} lg={6}>
                                {
                                    componentType?<TextField
                                    {...register(`test.${index}.item.${currentIndex}.${tab}.sematic`)}
                                    fullWidth
                                    value={systemVariables[tab].semantic}
                                    placeholder="Enter Semantic Value"
                                />:
                                <TextField
                                    {...register(`test.${index}.item.${currentIndex}.${tab}.sematic`)}
                                    fullWidth
                                    placeholder="Enter Semantic Value"
                                />
                                }
                                
                            </Grid>
                        </Grid>
                    </Grid>
                    <Divider />
                    <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                        <InputLabel horizontal>Resolution </InputLabel>
                    </Grid>
                    <Grid item xs={12} sm={8} lg={8}>
                        {
                            componentType?<TextField
                            {...register(`test.${index}.item.${currentIndex}.${tab}.resolution`)}
                            fullWidth
                            value={systemVariables[tab].resolution}
                            placeholder="Enter Resolution"
                        />:
                        
                        <TextField
                            {...register(`test.${index}.item.${currentIndex}.${tab}.resolution`)}
                            fullWidth
                            type={'number'}
                            InputProps={{
                                inputProps: { min: 0 }
                            }}
                            placeholder="Enter Resolution"
                        />}
                    </Grid>
                    <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                        <InputLabel horizontal>Upper Bound</InputLabel>
                    </Grid>
                    <Grid item xs={12} sm={9} lg={8}>
                        {
                            componentType?
                        
                        <TextField
                            type= {'text' }
                            {...register(`test.${index}.item.${currentIndex}.${tab}.upperBound`)}
                            fullWidth
                            value={systemVariables[tab].upperBound}
                            placeholder="Enter Upper Bound Value"
                        />:<TextField
                        type={componentType==='main_anf_statement'|| 'associated_anf_statement' ? 'text' : 'number'}
                        {...register(`test.${index}.item.${currentIndex}.${tab}.upperBound`)}
                        fullWidth
                        InputProps={{
                            inputProps: { min: 0 }
                        }}
                        type={'number'}
                        placeholder="Enter Upper Bound Value"
                    />}
                    </Grid>
                    <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                        <InputLabel horizontal>Include Upper Bound</InputLabel>
                    </Grid>
                    <Grid item xs={12} sm={9} lg={6}>
                        <FormControl>
                            <Controller
                                control={control}
                                {...register(`test.${index}.item.${currentIndex}.${tab}.upperBoundOptions`)}
                                render={({ field }) => componentType?(
                                    <RadioGroup
                                        row
                                        aria-label="upperBoundOptions"
                                        name="upperBoundOptions"
                                        {...field}
                                        onChange={(e) => {
                                            field.onChange(e.target.value);
                                        }}
                                        value={systemVariables[tab].upperBoundOptions}
                                    >
                                        <FormControlLabel value="yes" control={<Radio />} label="Yes" />
                                        <FormControlLabel value="no" control={<Radio />} label="No" />
                                        <FormControlLabel value="not" control={<Radio />} label="Not Answered" />
                                    </RadioGroup>
                                ):(
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
MeasureComponent.propTypes = {
    register: PropTypes.func,
    index: PropTypes.number,
    currentIndex: PropTypes.number,
    tab: PropTypes.string,
    control: PropTypes.any,
};
