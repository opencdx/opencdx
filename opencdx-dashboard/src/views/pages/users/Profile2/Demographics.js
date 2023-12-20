// material-ui
import { Grid, TextField, MenuItem } from '@mui/material';

// project imports
import { gridSpacing } from 'store/constant';

// ==============================|| PROFILE 2 - USER PROFILE ||============================== //
const ethinicity = ['White (Non-Hispanic)',
  'Black or African American',
  'Hispanic or Latino',
  'Asian',
  'Native American or Alaska Native',
  'Two or More Races',
  'Some Other Race']
const race = ['White',
  'Black or African American',
  'American Indian or Alaska Native',
  'Asian',
  'Native Hawaiian or Other Pacific Islander',
  'Two or More Races']
const nationality = ['American',
  'Mexican',
  'Puerto Rican',
  'Cuban',
  'Dominican',
  'Canadian',
  'Indian',
  'Chinese',
  'Filipino',
  'Vietnamese',
  'Korean',
  'Japanese',
  'British',
  'Irish',
  'German',
  'Italian',
  'Russian',
  'Israeli',
  'Iranian',
  'Nigerian',
  'Ethiopian',
  'South African',
  'Brazilian',
  'Colombian',
  'Peruvian',
  'Australian',
  'New Zealander',
  'Other'
]
const gender = [
  'Male',
  'Female',
  'Non-Binary',
  'Other',
  'Prefer not to say']


const Demographics = () => (
  <Grid container spacing={gridSpacing}>

    <Grid item xs={12} sm={6}>
      <TextField
        select
        label="Select a ethinicity..."
        variant="outlined"
      >
        {ethinicity?.map((template, index) => (
          <MenuItem key={index} value={template}>
            {template}
          </MenuItem>
        ))}
      </TextField>
    </Grid>
    <Grid item xs={12} sm={6}>
      <TextField
        select
        label="Select a race..."
        variant="outlined"
        fullWidth
      >
        {race?.map((template, index) => (
          <MenuItem key={index} value={template}>
            {template}
          </MenuItem>
        ))}
      </TextField>
    </Grid>
    <Grid item xs={12} sm={6}>
      <TextField
        select
        label="Select a nationality..."
        variant="outlined"
        fullWidth
      >
        {nationality?.map((template, index) => (
          <MenuItem key={index} value={template}>
            {template}
          </MenuItem>
        ))}
      </TextField>
    </Grid>
    <Grid item xs={12} sm={6}>
      <TextField
        select
        label="Select a gender..."
        variant="outlined"
        fullWidth
      >
        {gender?.map((template, index) => (
          <MenuItem key={index} value={template}>
            {template}
          </MenuItem>
        ))}
      </TextField>
    </Grid>
  </Grid>
);

export default Demographics;
