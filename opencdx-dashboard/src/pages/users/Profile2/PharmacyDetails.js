// material-ui
import { Grid, TextField } from '@mui/material';

// project imports
import { gridSpacing } from 'utils/store/constant';

// ==============================|| PROFILE 2 - USER PROFILE ||============================== //

const PharmacyDetails = () => (
    <Grid container spacing={gridSpacing}>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Pharmacy Name" defaultValue="Pharmacy Name" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Pharmacy Address" defaultValue="101 main street,CA,12345, USA" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Mobile number" defaultValue="1234567890" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Home number" defaultValue="1234567890" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Fax number" defaultValue="1234567890" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Email" defaultValue="test@opencdx.org" />
        </Grid>
    </Grid>
);

export default PharmacyDetails;
