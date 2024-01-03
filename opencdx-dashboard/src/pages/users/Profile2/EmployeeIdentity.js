// material-ui
import { Grid, TextField, MenuItem } from '@mui/material';

// project imports
import { gridSpacing } from 'utils/store/constant';

// ==============================|| PROFILE 2 - USER PROFILE ||============================== //
const status = [
    'Legal Permanent Resident',
    'U.S. Citizen',
    'Naturalized Citizen',
    'Asylum Seeker',
    'Refugee',
    'Temporary Protected Status (TPS) Holder',
    'Visitor (Tourist, Business, etc.)',
    'Student (F, M visa)',
    'H1B Visa Holder',
    'DACA (Deferred Action for Childhood Arrivals) Recipient',
    'Undocumented Immigrant',
    'Veteran',
    'Homeless',
    'Employed',
    'Unemployed',
    'Retired',
    'Married',
    'Single',
    'Divorced',
    'Widowed',
    'Student',
    'Disabled',
    'others'
];

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
            <TextField select label="Select a status..." variant="outlined" fullWidth>
                {status?.map((template, index) => (
                    <MenuItem key={index} value={template}>
                        {template}
                    </MenuItem>
                ))}
            </TextField>
        </Grid>
    </Grid>
);

export default EmployeeIdentity;
