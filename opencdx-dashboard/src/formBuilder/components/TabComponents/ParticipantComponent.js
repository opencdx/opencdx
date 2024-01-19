import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import { Divider, Grid, TextField } from '@mui/material';

import { MainCard } from '../ui-components/MainCard';
import { InputLabel } from '../ui-components/InputLabel';
import {systemVariables} from '../../store/constant';


export const ParticipantComponent = React.forwardRef(({ register, index, currentIndex, tab }, ref) => {
    const formData = JSON.parse(localStorage.getItem('anf-form'))
    const componentType = ['main_anf_statement', 'associated_anf_statement'].includes(formData.item[index]?.componentType);

    const [id, setId] = React.useState('');
    const [code, setCode] = React.useState('');
    const [practitionerValue, setPractitionerValue] = React.useState('');
    useEffect(() => {
        if(tab === 'authors' || tab === 'rangeParticipant'){
            setId(systemVariables[tab][0].id);
            setCode(systemVariables[tab][0].code);
            setPractitionerValue(systemVariables[tab][0].practitionerValue);
        }
        else{
            setId(systemVariables[tab].id);
            setCode(systemVariables[tab].code);
            setPractitionerValue(systemVariables[tab].practitionerValue);
        }
    }, [tab])
   
  
    return (
        <Grid item xs={12} lg={12} ref={ref}>
            <MainCard border>
                <Grid container spacing={2} alignItems="center">
                    <Grid item xs={12}>
                        <Grid container spacing={2} alignItems="center">
                            <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                                <InputLabel horizontal>ID</InputLabel>
                            </Grid>
                            <Grid item xs={12} sm={9} lg={8}>
                                {componentType ?
                                <TextField
                                    {...register(`test.${index}.item.${currentIndex}.${tab}.id`)}
                                    fullWidth
                                    placeholder="Enter ID Value"
                                    value={id}
                                />: 
                                <TextField
                                    {...register(`test.${index}.item.${currentIndex}.${tab}.id`)}
                                    fullWidth
                                    placeholder="Enter ID Value"
                                    />}
                            </Grid>

                            <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                                <InputLabel horizontal>Practitioner</InputLabel>
                            </Grid>
                            <Grid item xs={12} sm={9} lg={8}>
                                {componentType ?
                                <TextField
                                    {...register(`test.${index}.item.${currentIndex}.${tab}.practitionerValue`)}
                                    fullWidth
                                    placeholder="Enter Practitioner Value"
                                    value={practitionerValue}
                                />:
                                <TextField
                                    {...register(`test.${index}.item.${currentIndex}.${tab}.practitionerValue`)}
                                    fullWidth
                                    placeholder="Enter Practitioner Value"
                                    />}
                            </Grid>
                        </Grid>
                    </Grid>
                    <Divider />

                    <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                        <InputLabel horizontal>Code</InputLabel>
                    </Grid>
                    <Grid item xs={12} sm={9} lg={8}>
                        {componentType ?
                        <TextField
                            {...register(`test.${index}.item.${currentIndex}.${tab}.code`)}
                            fullWidth
                            placeholder="Enter Code Value"
                            value={code}
                        />: 
                        <TextField
                            {...register(`test.${index}.item.${currentIndex}.${tab}.code`)}
                            fullWidth
                            placeholder="Enter Code Value"
                            />}
                    </Grid>
                </Grid>
            </MainCard>
        </Grid>
    );
});
ParticipantComponent.propTypes = {
    register: PropTypes.func,
    index: PropTypes.number,
    currentIndex: PropTypes.number,
    tab: PropTypes.string
};
