// material-ui
import { Grid, TextField } from '@mui/material';

// project imports
import { gridSpacing } from 'store/constant';

// ==============================|| PROFILE 2 - USER PROFILE ||============================== //

const Communication = () => (
    <Grid container spacing={gridSpacing}>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Languagee" defaultValue="En" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Preferred" defaultValue="True" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Time Zone" defaultValue="EST" />
        </Grid>
    </Grid>
);

export default Communication;
