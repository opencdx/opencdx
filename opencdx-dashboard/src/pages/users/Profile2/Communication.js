// material-ui
import { Grid, TextField, MenuItem } from '@mui/material';

// project imports
import { gridSpacing } from 'utils/store/constant';

// ==============================|| PROFILE 2 - USER PROFILE ||============================== //
const languages = [
    'English',
    'Spanish',
    'Chinese (Mandarin and Cantonese)',
    'Tagalog',
    'Vietnamese',
    'Arabic',
    'French (including Haitian Creole)',
    'German',
    'Italian',
    'Russian',
    'Portuguese',
    'Korean',
    'Japanese',
    'Persian (Farsi)',
    'Hindi and other Indian languages'
];
const Communication = () => (
    <Grid container spacing={gridSpacing}>
        <Grid item xs={12} sm={6}>
            <TextField select label="Select a Language..." variant="outlined" fullWidth>
                {languages?.map((template, index) => (
                    <MenuItem key={index} value={template}>
                        {template}
                    </MenuItem>
                ))}
            </TextField>
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
