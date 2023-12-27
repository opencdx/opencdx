import React from 'react';
import { Grid, Typography, Button } from '@mui/material';
import HomeTwoToneIcon from '@mui/icons-material/HomeTwoTone';
import { gridSpacing } from 'utils/store/constant';
import lottie from 'lottie-web';
import { useNavigate } from 'react-router-dom';

// ==============================|| ERROR PAGE ||============================== //

const ErrorPage = () => {
    const navigate = useNavigate();

    React.useEffect(() => {
        // Load the Lottie animation
        const animation = lottie.loadAnimation({
            container: document.getElementById('error-animation'), // Replace with the ID of the container element
            renderer: 'svg',
            loop: true,
            autoplay: true,
            path: '../../../assets/404.json' // Replace with path to animation JSON
        });

        return () => {
            // Cleanup the animation on unmount
            animation.destroy();
        };
    }, []);

    const handleButtonClick = () => {
        navigate('/dashboard'); // Replace '/dashboard' with the actual path to the DashboardPage
    };

    return (
        <Grid container spacing={gridSpacing} style={{ textAlign: 'center' }}>
            <Grid item xs={12}>
                <div id="error-animation" style={{ width: '100%', height: '200px' }}></div>
            </Grid>
            <Grid item xs={12}>
                <Typography variant="h1" component="div">
                    Something is wrong
                </Typography>
            </Grid>
            <Grid item xs={12}>
                <Typography variant="body2">The page you are looking for was moved, removed, renamed, or might never exist!</Typography>
            </Grid>
            <Grid item xs={12}>
                <Button variant="contained" size="large" onClick={handleButtonClick}>
                    <HomeTwoToneIcon sx={{ fontSize: '1.3rem', mr: 0.75 }} /> Home
                </Button>
            </Grid>
        </Grid>
    );
};

export default ErrorPage;
