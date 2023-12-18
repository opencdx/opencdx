import { Grid, TextField } from '@mui/material';

// project imports
import { gridSpacing } from 'store/constant';

// ==============================|| PROFILE 2 - USER PROFILE ||============================== //

const Demographics = () => (
    <Grid container spacing={gridSpacing}>
        
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Ethnicity" defaultValue="Ethnicity" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Race" defaultValue="Race" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Nationality" defaultValue="USA" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Gender" defaultValue="Male" />
        </Grid>
           </Grid>
);

export default Demographics;
