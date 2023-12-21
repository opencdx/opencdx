// material-ui
import { Grid, TextField, MenuItem } from '@mui/material';

// project imports
import { gridSpacing } from 'store/constant';

// ==============================|| PROFILE 2 - USER PROFILE ||============================== //
const degree = [
    'High School Diploma',
    'Associate Degree',
    "Bachelor's Degree",
    "Master's Degree",
    'Doctoral Degree (Ph.D.)',
    'Professional Degrees (e.g., MD, JD, DDS)'
];
const university = [
    'Harvard University',
    'Massachusetts Institute of Technology (MIT)',
    'Stanford University',
    'California Institute of Technology (Caltech)',
    'Princeton University',
    'Yale University',
    'Columbia University',
    'University of Chicago',
    'University of Pennsylvania',
    'Johns Hopkins University',
    'University of California, Berkeley',
    'University of California, Los Angeles (UCLA)',
    'University of Michigan, Ann Arbor',
    'University of Texas at Austin',
    'Massachusetts General Hospital Institute of Health Professions',
    'others'
];

const Education = () => (
    <Grid container spacing={gridSpacing}>
        <Grid item xs={12} sm={6}>
            <TextField select label="Select a degree..." variant="outlined" fullWidth>
                {degree?.map((template, index) => (
                    <MenuItem key={index} value={template}>
                        {template}
                    </MenuItem>
                ))}
            </TextField>
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField select label="Select a university..." variant="outlined" fullWidth>
                {university?.map((template, index) => (
                    <MenuItem key={index} value={template}>
                        {template}
                    </MenuItem>
                ))}
            </TextField>
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
