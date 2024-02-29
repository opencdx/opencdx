import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import { Divider, Grid, TextField } from '@mui/material';

import { MainCard } from '../ui-components/MainCard';
import { InputLabel } from '../ui-components/InputLabel';
import { systemVariables } from '../../store/constant';

export const ParticipantComponent = React.forwardRef(({ register, index, currentIndex, tab }, ref) => {
    const formData = JSON.parse(localStorage.getItem('anf-form'));
    const componentType = ['main_anf_statement', 'associated_anf_statement'].includes(formData.item[index]?.componentType);
    const id = formData.item[index].item[currentIndex][tab]?.id;
    const code = formData.item[index].item[currentIndex][tab]?.code;
    const practitionerValue = formData.item[index].item[currentIndex][tab]?.practitionerValue;
    const [idState, setId] = React.useState('');
    const [codeState, setCode] = React.useState('');
    const [practitionerValueState, setPractitionerValue] = React.useState('');
    useEffect(() => {
        if (tab === 'authors' || tab === 'rangeParticipant') {
            setId(id?id:systemVariables[tab][0].id);
            setCode(code?code:systemVariables[tab][0].code);
            setPractitionerValue(practitionerValue?practitionerValue:systemVariables[tab][0].practitionerValue);
        } else {
            setId(id?id:systemVariables[tab].id);
            setCode(code?code:systemVariables[tab].code);
            setPractitionerValue(practitionerValue?practitionerValue:systemVariables[tab].practitionerValue);
        }
    }, [tab]);

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
                                {componentType ? (
                                    <TextField
                                        {...register(`test.${index}.item.${currentIndex}.${tab}.id`)}
                                        fullWidth
                                        placeholder="Enter ID Value"
                                        value={idState}
                                        defaultValue={idState}
                                        onChange={(e) => setId(e.target.value)}
                                    />
                                ) : (
                                    <TextField
                                        {...register(`test.${index}.item.${currentIndex}.${tab}.id`)}
                                        fullWidth
                                        placeholder="Enter ID Value"
                                    />
                                )}
                            </Grid>

                            <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                                <InputLabel horizontal>Practitioner</InputLabel>
                            </Grid>
                            <Grid item xs={12} sm={9} lg={8}>
                                {componentType ? (
                                    <TextField
                                        {...register(`test.${index}.item.${currentIndex}.${tab}.practitionerValue`)}
                                        fullWidth
                                        placeholder="Enter Practitioner Value"
                                        value={practitionerValueState}
                                        onChange={(e) => setPractitionerValue(e.target.value)}
                                    />
                                ) : (
                                    <TextField
                                        {...register(`test.${index}.item.${currentIndex}.${tab}.practitionerValue`)}
                                        fullWidth
                                        placeholder="Enter Practitioner Value"
                                    />
                                )}
                            </Grid>
                        </Grid>
                    </Grid>
                    <Divider />

                    <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                        <InputLabel horizontal>Code</InputLabel>
                    </Grid>
                    <Grid item xs={12} sm={9} lg={8}>
                        {componentType ? (
                            <TextField
                                {...register(`test.${index}.item.${currentIndex}.${tab}.code`)}
                                fullWidth
                                placeholder="Enter Code Value"
                                value={codeState}
                                onChange={(e) => setCode(e.target.value)}
                            />
                        ) : (
                            <TextField
                                {...register(`test.${index}.item.${currentIndex}.${tab}.code`)}
                                fullWidth
                                placeholder="Enter Code Value"
                            />
                        )}
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
