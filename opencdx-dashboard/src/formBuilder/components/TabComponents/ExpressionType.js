import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import { Divider, Grid, TextField } from '@mui/material';

import { MainCard } from '../ui-components/MainCard';
import { InputLabel } from '../ui-components/InputLabel';
import {systemVariables} from '../../store/constant';


export const ExpressionType = React.forwardRef(({ register, index, currentIndex, tab }, ref) => {
    const formData = JSON.parse(localStorage.getItem('anf-form'))
    const componentType = ['main_anf_statement', 'associated_anf_statement'].includes(formData.item[index]?.componentType);
    return (
        <Grid item xs={12} lg={12} ref={ref}>
            <MainCard border>
                <Grid container spacing={2} alignItems="center">
                    <Grid item xs={12}>
                        <Grid container spacing={2} alignItems="center">
                            <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                                <InputLabel horizontal>Expression Type</InputLabel>
                            </Grid>
                            <Grid item xs={12} sm={9} lg={8}>
                                {componentType ?
                                <TextField
                                    {...register(`test.${index}.item.${currentIndex}.${tab}.expressionType`)}
                                    fullWidth
                                    placeholder="Enter ExpressionType Value"
                                    value={systemVariables[tab].expressionType}
                                />: 
                                <TextField
                                    {...register(`test.${index}.item.${currentIndex}.${tab}.expressionType`)}
                                    fullWidth
                                    placeholder="Enter ExpressionType Value"
                                    />}
                            </Grid>

                            <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                                <InputLabel horizontal>Expression Language</InputLabel>
                            </Grid>
                            <Grid item xs={12} sm={9} lg={8}>
                                {componentType ?
                                <TextField
                                    {...register(`test.${index}.item.${currentIndex}.${tab}.expressionLanguage`)}
                                    fullWidth
                                    placeholder="Enter ExpressionLanguage Value"
                                    value={systemVariables[tab].expressionLanguage}
                                />:
                                <TextField
                                    {...register(`test.${index}.item.${currentIndex}.${tab}.expressionLanguage`)}
                                    fullWidth
                                    placeholder="Enter ExpressionLanguage Value"
                                    />}
                            </Grid>
                        </Grid>
                    </Grid>

                    <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                        <InputLabel horizontal>Expression Value</InputLabel>
                    </Grid>
                    <Grid item xs={12} sm={9} lg={8}>
                        {componentType ?
                        <TextField
                            {...register(`test.${index}.item.${currentIndex}.${tab}.expressionValue`)}
                            fullWidth
                            placeholder="Enter ExpressionValue Value"
                            value={systemVariables[tab].expressionValue}
                        />: 
                        <TextField
                            {...register(`test.${index}.item.${currentIndex}.${tab}.expressionValue`)}
                            fullWidth
                            placeholder="Enter ExpressionValue Value"
                            />}
                    </Grid>
                    <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                        <InputLabel horizontal>Expression Value</InputLabel>
                    </Grid>
                    <Grid item xs={12} sm={9} lg={8}>
                        {componentType ?
                        <TextField
                            {...register(`test.${index}.item.${currentIndex}.${tab}.expressionDescription`)}
                            fullWidth
                            placeholder="Enter Expression Description Value"
                            value={systemVariables[tab].expressionDescription}
                        />: 
                        <TextField
                            {...register(`test.${index}.item.${currentIndex}.${tab}.expressionDescription`)}
                            fullWidth
                            placeholder="Enter expressionDescription Value"
                            />}
                    </Grid>
                </Grid>
            </MainCard>
        </Grid>
    );
});
ExpressionType.propTypes = {
    register: PropTypes.func,
    index: PropTypes.number,
    currentIndex: PropTypes.number,
    tab: PropTypes.string
};
