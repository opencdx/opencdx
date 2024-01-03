import PropTypes from 'prop-types';
import { memo } from 'react';
import { styled, alpha } from '@mui/material/styles';

// material-ui
import { Box, Slider, Switch, Typography } from '@mui/material';

// third-party
import { format } from 'date-fns';

// project-import

// ==============================|| HEATMAP - CONTROL ||============================== //

const ControlPanel = ({ startTime, endTime, allDays, selectedTime, onChangeTime, onChangeAllDays }) => {
    const ControlPanelStyled = styled(Box)(({ theme }) => ({
        backdropFilter: `blur(4px)`,
        WebkitBackdropFilter: `blur(4px)`,
        backgroundColor: alpha(theme.palette.background.paper, 0.85),
        zIndex: 9,
        minWidth: 200,
        position: 'absolute',
        top: 8,
        right: 8,
        padding: 20,
        borderRadius: 4
    }));
    const day = 24 * 60 * 60 * 1000;
    const days = Math.round((endTime - startTime) / day);
    const selectedDay = Math.round((selectedTime - startTime) / day);

    const handleChangeDays = (value) => {
        const daysToAdd = value;
        const newTime = startTime + daysToAdd * day;
        onChangeTime(newTime);
    };

    return (
        <ControlPanelStyled>
            <Box
                sx={{
                    width: '100%',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'space-between'
                }}
            >
                <Typography variant="subtitle2">All Days</Typography>
                <Switch size="small" checked={allDays} onChange={(event) => onChangeAllDays(event.target.checked)} />
            </Box>

            <br />

            <Typography gutterBottom variant="body2" sx={{ color: allDays ? 'text.disabled' : 'text.primary' }}>
                Each Day: {format(new Date(selectedTime), 'dd MMM yyyy')}
            </Typography>

            <Slider
                min={1}
                step={1}
                max={days}
                disabled={allDays}
                value={selectedDay}
                color="primary"
                onChange={(event, newValue) => {
                    if (typeof newValue === 'number') handleChangeDays(newValue);
                }}
            />
        </ControlPanelStyled>
    );
};

ControlPanel.propTypes = {
    startTime: PropTypes.number,
    endTime: PropTypes.number,
    allDays: PropTypes.bool,
    selectedTime: PropTypes.number,
    onChangeTime: PropTypes.func,
    onChangeAllDays: PropTypes.func
};

export default memo(ControlPanel);
