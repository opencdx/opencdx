// material-ui
import { Grid,  TextField } from '@mui/material';

// project imports
import { gridSpacing } from 'store/constant';

// ==============================|| PROFILE 2 - USER PROFILE ||============================== //

const CurrentMedications = () => (
    <Grid container spacing={gridSpacing}>
        
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Name" defaultValue="Singular" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Dosage" defaultValue="5 mg" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Instructions" defaultValue="Take 1 pill at Night" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Route Of Administration " defaultValue="Oral" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Frequency" defaultValue="Daily" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Start Date" defaultValue="1976/12/26" />
        </Grid>
         <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Prescribing Doctor" defaultValue="Dr. OpenCDX" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Pharmacy" defaultValue="Pharmacy" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Is Prescription" defaultValue="True" />
        </Grid>
    </Grid>
);

export default CurrentMedications;
