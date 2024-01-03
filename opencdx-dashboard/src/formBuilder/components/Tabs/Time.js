import React from 'react';
import PropTypes from 'prop-types';
import { MeasureComponent } from '../TabComponents/MeasureComponent';
import { Button } from '@mui/material';
import { Dialog, DialogTitle, DialogContent, DialogActions } from '@mui/material';
import QuestionsList from '../ui-components/QuestionsList';

const Time = React.forwardRef(({ control, register, index, currentIndex, getValues }, ref) => {
    const [open, setOpen] = React.useState(false);
    const handleClickOpen = () => {
        setOpen(true);
    };
    const handleCloseDialog = () => {
        setOpen(false);
    };
    return (
        <>
            <Button variant="contained" color="primary" onClick={handleClickOpen}>
                Time
            </Button>
            <Dialog open={open} onClose={handleCloseDialog} fullWidth>
                <DialogTitle>Dialog Title</DialogTitle>
                <DialogContent>
                    <QuestionsList index={index} currentIndex={currentIndex} getValues={getValues} tab="time" />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseDialog}>Close</Button>
                </DialogActions>
            </Dialog>

            <MeasureComponent {...{ control, register, index, currentIndex }} tab="time" ref={ref} />
        </>
    );
});
Time.propTypes = {
    register: PropTypes.func,
    control: PropTypes.object,
    index: PropTypes.number,
    currentIndex: PropTypes.number,
    getValues: PropTypes.any
};
export { Time };
