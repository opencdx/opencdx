// material-ui
import { Grid, TextField, MenuItem } from '@mui/material';

// project imports
import { gridSpacing } from 'utils/store/constant';

// ==============================|| PROFILE 2 - USER PROFILE ||============================== //
const locality = [
    'New York City, NY',
    'Los Angeles, CA',
    'Chicago, IL',
    'Houston, TX',
    'Phoenix, AZ',
    'Philadelphia, PA',
    'San Antonio, TX',
    'San Diego, CA',
    'Dallas, TX',
    'San Jose, CA',
    'Austin, TX',
    'Jacksonville, FL',
    'San Francisco, CA',
    'Charlotte, NC',
    'Seattle, WA',
    'Denver, CO',
    'Washington, D.C.',
    'Boston, MA',
    'Detroit, MI',
    'Nashville, TN',
    'Memphis, TN',
    'Portland, OR',
    'Oklahoma City, OK',
    'Las Vegas, NV',
    'Louisville, KY',
    'Baltimore, MD',
    'Milwaukee, WI',
    'Albuquerque, NM',
    'Tucson, AZ',
    'Fresno, CA',
    'Sacramento, CA',
    'Kansas City, MO',
    'Atlanta, GA',
    'Long Beach, CA',
    'Mesa, AZ',
    'Raleigh, NC',
    'Omaha, NE',
    'Miami, FL',
    'Oakland, CA',
    'Minneapolis, MN',
    'Tulsa, OK',
    'Wichita, KS',
    'New Orleans, LA',
    'Arlington, TX',
    'Cleveland, OH',
    'Bakersfield, CA',
    'Tampa, FL'
];
const district = [
    'Washington, D.C. (Federal District)',
    'Congressional District 1, AL',
    'School District of Philadelphia, PA',
    'San Francisco Unified School District, CA',
    'Houston Independent School District, TX',
    'Chinatown District, San Francisco, CA',
    'French Quarter, New Orleans, LA',
    'Financial District, New York City, NY',
    'Historic District, Savannah, GA',
    'Warehouse District, Minneapolis, MN',
    'Arts District, Los Angeles, CA',
    'Historic District, Charleston, SC',
    'Downtown District, Dallas, TX',
    'Medical District, Chicago, IL',
    'Riverfront District, Portland, OR',
    'Uptown District, New Orleans, LA',
    'Technology District, Austin, TX',
    'Fashion District, Los Angeles, CA',
    'Warehouse District, Cleveland, OH',
    'Historic District, Annapolis, MD',
    'The Loop, Chicago, IL',
    'Cherry Creek North, Denver, CO',
    'Financial District, San Francisco, CA',
    'Warehouse District, Phoenix, AZ',
    'Pearl District, Portland, OR',
    'Historic District, Savannah, GA',
    'Back Bay, Boston, MA',
    'Historic District, Santa Fe, NM',
    'Short North, Columbus, OH',
    'River Market District, Little Rock, AR',
    'Historic District, Alexandria, VA',
    'Warehouse District, Raleigh, NC'
];
const vaccineType = [
    'Pfizer-BioNTech (Comirnaty)',
    'Moderna',
    'Johnson & Johnson/Janssen',
    'AstraZeneca',
    'Novavax',
    'Sinopharm',
    'Sinovac',
    'COVAXIN',
    'others'
];
const FacilityType = ['Hospitals', 'clinics', 'medical center'];

const VaccinationAdministed = () => (
    <Grid container spacing={gridSpacing}>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Administration Date" defaultValue="2021/06/01" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Fips" defaultValue="12345" />
        </Grid>

        <Grid item xs={12} sm={6}>
            <TextField select label="Select a district..." variant="outlined" fullWidth>
                {district?.map((template, index) => (
                    <MenuItem key={index} value={template}>
                        {template}
                    </MenuItem>
                ))}
            </TextField>
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField select label="Select a facility type..." variant="outlined" fullWidth>
                {FacilityType?.map((template, index) => (
                    <MenuItem key={index} value={template}>
                        {template}
                    </MenuItem>
                ))}
            </TextField>
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField select label="Select a localitiy..." variant="outlined" fullWidth>
                {locality?.map((template, index) => (
                    <MenuItem key={index} value={template}>
                        {template}
                    </MenuItem>
                ))}
            </TextField>
        </Grid>

        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Manufacturer" defaultValue="655787ff36bf9e6b6453412a" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Dose Number" defaultValue="20" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField select label="Select a vaccine type..." variant="outlined" fullWidth>
                {vaccineType?.map((template, index) => (
                    <MenuItem key={index} value={template}>
                        {template}
                    </MenuItem>
                ))}
            </TextField>
        </Grid>
    </Grid>
);

export default VaccinationAdministed;
