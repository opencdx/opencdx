import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import { Divider, Grid, TextField } from '@mui/material';

import { MainCard } from '../ui-components/MainCard';
import { InputLabel } from '../ui-components/InputLabel';
import { systemVariables, statementType } from '../../store/constant';
import { useAnfFormStore } from '../../utils/useAnfFormStore';

export const ParticipantComponent = React.forwardRef(({ register, index, currentIndex, tab }, ref) => {
    const { formData } = useAnfFormStore();
    const componentType = [statementType.MAIN, statementType.ASSOCIATED].includes(formData.item[index]?.componentType);

    const { id, code, practitionerValue } = formData.item[index]?.item?.[currentIndex]?.[tab] || '';
    const [state, setState] = React.useState({
        id: '',
        code: '',
        practitionerValue: ''
    });

    useEffect(() => {
        const systemVariable = tab === 'authors' || tab === 'circumstanceChoice.circumstance' || tab === 'circumstanceChoice.participant' ? systemVariables[tab][0] : systemVariables[tab];

        if (componentType) {
            setState((prevState) => ({
                id: id || systemVariable.id || prevState.id,
                code: code || systemVariable.code || prevState.code,
                practitionerValue: practitionerValue || systemVariable.practitionerValue || prevState.practitionerValue
            }));
        } else {
            setState((prevState) => ({
                id: id || prevState.id,
                code: code || prevState.code,
                practitionerValue: practitionerValue || prevState.practitionerValue
            }));
        }
    }, [tab, id, code, componentType, practitionerValue]);

    return (
        <Grid item xs={12} lg={12} ref={ref}>
            <MainCard border>
                <Grid container spacing={2} alignItems="center">
                    <Grid item xs={12}>
                        <Grid container spacing={2} alignItems="center">
                            {/* ... other grid items ... */}

                            {/* ID */}
                            {renderTextField(
                                register,
                                'ID',
                                `item.${index}.item.${currentIndex}.anfStatementConnector[0].anfStatement.${tab}.id`,
                                'Enter ID Value',
                                state.id,
                                (e) => setState({ ...state, id: e.target.value })
                            )}

                            {/* Practitioner */}
                            {renderTextField(
                                register,
                                'Practitioner',
                                `item.${index}.item.${currentIndex}.anfStatementConnector[0].anfStatement.${tab}.practitionerValue`,
                                'Enter Practitioner Value',
                                state.practitionerValue,
                                (e) => setState({ ...state, practitionerValue: e.target.value })
                            )}

                            {/* Code */}
                            {renderTextField(
                                register,
                                'Code',
                                `item.${index}.item.${currentIndex}.anfStatementConnector[0].anfStatement.${tab}.code`,
                                'Enter Code Value',
                                state.code,
                                (e) => setState({ ...state, code: e.target.value })
                            )}
                        </Grid>
                    </Grid>
                    <Divider />

                    {/* ... other grid items ... */}
                </Grid>
            </MainCard>
        </Grid>
    );
});

// Helper function to render text fields
const renderTextField = (register, label, name, placeholder, value, onChange) => (
    <>
        <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
            <InputLabel horizontal>{label}</InputLabel>
        </Grid>
        <Grid item xs={12} sm={9} lg={8}>
            <TextField {...register(name)} fullWidth placeholder={placeholder} value={value} defaultValue={value} onChange={onChange} />
        </Grid>
    </>
);

ParticipantComponent.propTypes = {
    register: PropTypes.func,
    index: PropTypes.number,
    currentIndex: PropTypes.number,
    tab: PropTypes.string
};
