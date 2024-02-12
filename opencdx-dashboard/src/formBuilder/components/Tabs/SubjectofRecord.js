import React from 'react';
import PropTypes from 'prop-types';

import { Button, Grid } from '@mui/material';
import { Dialog, DialogTitle, DialogContent, DialogActions } from '@mui/material';
import SettingsIcon from '@mui/icons-material/Settings';
import QuestionsList from '../ui-components/QuestionsList';
import { ParticipantComponent } from '../TabComponents/ParticipantComponent';

const SubjectofRecord = React.forwardRef(({ register, index, getValues, currentIndex }, ref) => {
    const [open, setOpen] = React.useState(false);
    const handleClickOpen = () => {
        setOpen(true);
    };
    const handleCloseDialog = () => {
        setOpen(false);
    };

    return (
        <>
            <Grid container>
                <Grid item xs={12} lg={12} ref={ref} justifyContent="flex-end" sx={{ display: 'flex', pb: 2 }}>
                    <Button variant="contained" color="primary" size="small" onClick={handleClickOpen}>
                        <SettingsIcon /> System Variables
                    </Button>
                </Grid>
            </Grid>
            <Dialog open={open} onClose={handleCloseDialog} fullWidth>
                <DialogTitle>System Variables</DialogTitle>
                <DialogContent>
                    <QuestionsList index={index} currentIndex={currentIndex} getValues={getValues} tab="time" />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseDialog}>Close</Button>
                </DialogActions>
            </Dialog>
            <ParticipantComponent {...{ register, index, currentIndex }} tab="subjectOfRecord" ref={ref} />
        </>
    );
});
SubjectofRecord.propTypes = {
    register: PropTypes.func,
    index: PropTypes.number,
    currentIndex: PropTypes.number,
    getValues: PropTypes.any
};
export { SubjectofRecord };
