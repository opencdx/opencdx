// material-ui
import { Grid, TextField } from '@mui/material';

// project imports
import { gridSpacing } from 'store/constant';

// ==============================|| PROFILE 2 - USER PROFILE ||============================== //

const Education = () => (
    <Grid container spacing={gridSpacing}>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Degree" defaultValue="BA" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Institution" defaultValue="University" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Start Date" defaultValue="1992/08/01" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Completion Date" defaultValue="1996/05/30" />
        </Grid>
    </Grid>
);

export default Education;
