import React from 'react';
import PropTypes from 'prop-types';
import { Button, Grid, Typography } from '@mui/material';
import { Dialog, DialogTitle, DialogContent, DialogActions } from '@mui/material';
import DisplaySettingsIcon from '@mui/icons-material/DisplaySettings';
import CodeIcon from '@mui/icons-material/Code';

import QuestionsList from './QuestionsList';

const SystemVariables = React.forwardRef(({ index, currentIndex, getValues, tab }, ref) => {
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
                    <Button variant="contained" color="warning" size="small" onClick={handleClickOpen}>
                        <DisplaySettingsIcon />
                        <Typography sx={{ pl: 1 }}>System Variables</Typography>
                    </Button>

                    <Button variant="contained" color="warning" size="small" onClick={handleClickOpen} sx={{ ml: 2 }}>
                        <CodeIcon />
                        <Typography sx={{ pl: 1 }}>Code Lookup</Typography>
                    </Button>
                </Grid>
            </Grid>
            <Dialog open={open} onClose={handleCloseDialog} fullWidth>
                <DialogTitle>System Variables</DialogTitle>
                <DialogContent>
                    <QuestionsList index={index} currentIndex={currentIndex} getValues={getValues} tab={tab} />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseDialog}>Close</Button>
                </DialogActions>
            </Dialog>
        </>
    );
});
SystemVariables.propTypes = {
    index: PropTypes.number,
    currentIndex: PropTypes.number,
    getValues: PropTypes.func,
    tab: PropTypes.string
};

export { SystemVariables };
