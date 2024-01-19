import React from 'react';
import PropTypes from 'prop-types';
import { ParticipantComponent } from '../TabComponents/ParticipantComponent';
import { Button, Grid } from '@mui/material';
import { Dialog, DialogTitle, DialogContent, DialogActions } from '@mui/material';
import SettingsIcon from '@mui/icons-material/Settings';

import QuestionsList from '../ui-components/QuestionsList';

const Authors = React.forwardRef(({ control, register, index, currentIndex, getValues }, ref) => {
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
                <Grid item xs={12} lg={12} ref={ref} justifyContent="flex-end" sx={{ display: 'flex' }}>
                    <Button variant="contained" color="primary" onClick={handleClickOpen}>
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
            <ParticipantComponent {...{ control, register, index, currentIndex }} tab="authors" ref={ref} />
        </>
    );
});
Authors.propTypes = {
    register: PropTypes.func,
    control: PropTypes.object,
    index: PropTypes.number,
    currentIndex: PropTypes.number
};

export { Authors };
