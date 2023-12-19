// material-ui

// project imports
import { Grid, TextField } from '@mui/material';

// project imports
import { gridSpacing } from 'store/constant';

// ==============================|| PROFILE 2 - USER PROFILE ||============================== //

const KnownAllegeries = () => (
    <Grid container spacing={gridSpacing}>
        
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Allergen" defaultValue="Evergreen Trees" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Reaction" defaultValue="Respiratory Distress5" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Is Severe" defaultValue="True" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="On set Date" defaultValue="1975/12/20" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Last Occurrence" defaultValue=" 1976/12/25" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Notes" defaultValue="Chrirstmas Trees" />
        </Grid>
       
    </Grid>
);

export default KnownAllegeries;
