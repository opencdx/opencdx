import React from 'react';
import PropTypes from 'prop-types';
import { Grid, TextField } from '@mui/material';

import { MainCard } from '../ui-components/MainCard';
import { SystemVariables } from '../ui-components/SystemVariables';

import { InputLabel } from '../ui-components/InputLabel';
import { systemVariables, statementType } from '../../store/constant';
import { useAnfFormStore } from '../../utils/useAnfFormStore';

const SubjectOfInformation = React.forwardRef(({ register, index, currentIndex, getValues }, ref) => {
    const { formData } = useAnfFormStore();

    const componentType = [statementType.MAIN, statementType.ASSOCIATED].includes(formData.item[index]?.componentType);
    return (
        <Grid item xs={12} lg={12} ref={ref}>
            <SystemVariables index={index} currentIndex={currentIndex} getValues={getValues} tab={'subjectOfInformation'} />
            <MainCard border>
                <Grid container spacing={2} alignItems="center">
                    <Grid item xs={12}>
                        <Grid container spacing={2} alignItems="center">
                            <Grid item xs={12} sm={3} lg={4} sx={{ pt: { xs: 2, sm: '0 !important' } }}>
                                <InputLabel horizontal>Subject Of Information</InputLabel>
                            </Grid>
                            <Grid item xs={12} sm={9} lg={6}>
                                {componentType ? (
                                    <TextField
                                        {...register(
                                            `item.${index}.item.${currentIndex}.anfStatementConnector[0].anfStatement.subjectOfInformation`
                                        )}
                                        fullWidth
                                        defaultValue={systemVariables['subjectOfInformation'].subjectOfRecord.match(/(?<=:\s*)[^\s]+/)[0]}
                                        placeholder="Enter Subject Of Information"
                                    />
                                ) : (
                                    <TextField
                                        {...register(
                                            `item.${index}.item.${currentIndex}.anfStatementConnector[0].anfStatement.subjectOfInformation`
                                        )}
                                        fullWidth
                                        placeholder="Enter Subject Of Information"
                                    />
                                )}
                            </Grid>
                        </Grid>
                    </Grid>
                </Grid>
            </MainCard>
        </Grid>
    );
});
SubjectOfInformation.propTypes = {
    register: PropTypes.func,
    index: PropTypes.number,
    currentIndex: PropTypes.number,
    tab: PropTypes.string,
    getValues: PropTypes.func
};
export { SubjectOfInformation };
