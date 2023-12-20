// material-ui
import { Grid, TextField } from '@mui/material';

// project imports
import { gridSpacing } from 'store/constant';

// ==============================|| PROFILE 2 - USER PROFILE ||============================== //

const VaccinationAdministed = () => (
    <Grid container spacing={gridSpacing}>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Administration Date" defaultValue="2021/06/01" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Fips" defaultValue="12345" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Locality" defaultValue="CA" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Health District" defaultValue="District" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Facility Type" defaultValue="Clinic" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Manufacturer" defaultValue="655787ff36bf9e6b6453412a" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Dose Number" defaultValue="20" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Vaccine Type" defaultValue="COVID-19" />
        </Grid>
    </Grid>
);

export default VaccinationAdministed;
