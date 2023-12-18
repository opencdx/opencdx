// material-ui
import { Grid, TextField } from '@mui/material';

// project imports
import { gridSpacing } from 'store/constant';

// ==============================|| PROFILE 2 - USER PROFILE ||============================== //

const EmployeeIdentity = () => (
    <Grid container spacing={gridSpacing}>
        
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="OrganizationId" defaultValue="OrganizationId" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Workspace Id" defaultValue="Workspace Id" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Employee Id" defaultValue="Employee Id" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Identity Verified" defaultValue="True" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Status" defaultValue="Full Time" />
        </Grid>
        
    </Grid>
);

export default EmployeeIdentity;
